package com.ruoyi.web.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.StudentApply;
import com.ruoyi.system.service.IStudentApplyService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
 
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信公众号登录控制器
 * 核心流程：授权获取code -> 换取OpenID -> 绑定/创建账号 -> 生成token
 *
 * @author 西部计划校友平台
 * @date 2026-02-01
 */
@Api(tags = "微信登录接口")
@RestController
@RequestMapping("/wx/login")
public class WxLoginController {
    @Resource
    private WxMpService wxMpService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IStudentApplyService studentApplyService;
    @Resource
    private TokenService tokenService;
    @Resource
    private RedisCache redisCache;

    /**
     * 微信授权回调地址（与数据库配置一致）
     */
    @Value("${wx.mp.loginRedirectUrl}")
    private String loginRedirectUrl;

    /**
     * 1. 生成微信授权URL
     */
    @ApiOperation("生成微信授权URL")
    @GetMapping("/authorize")
    public AjaxResult authorize() {
        String url = wxMpService.getOAuth2Service().buildAuthorizationUrl(loginRedirectUrl, "snsapi_base", "STATE");
        return AjaxResult.success(url);
    }

    /**
     * 2. 微信授权回调，处理登录
     */
    @ApiOperation("微信授权回调登录")
    @GetMapping("/callback")
    public AjaxResult callback(@RequestParam String code) {
        try {
            // 第一步：通过code换取OpenID（snsapi_base仅获取OpenID，无需用户信息）
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            String openId = accessToken.getOpenId();
            if (StringUtils.isBlank(openId)) {
                return AjaxResult.error("获取微信OpenID失败");
            }

            // 第二步：校验OpenID是否已绑定系统账号
            StudentApply apply = studentApplyService.selectStudentApplyByWxOpenid(openId);
            if (apply == null || apply.getUserId() == null) {
                // 未绑定：返回OpenID，引导前端完善信息绑定
                Map<String, Object> data = new HashMap<>();
                data.put("isBind", false);
                data.put("openId", openId);
                // OpenID存入Redis，有效期5分钟（绑定用）
                redisCache.setCacheObject(Constants.WX_OPENID_KEY + openId, openId, 5, TimeUnit.MINUTES);
                return AjaxResult.success(data);
            }

            // 第三步：已绑定：根据用户ID获取系统用户，生成登录token
            SysUser sysUser = sysUserService.selectUserById(Long.parseLong(apply.getUserId()) );
            if (sysUser == null || sysUser.getStatus() == "0") {
                return AjaxResult.error("账号不存在或已禁用");
            }
            SysLoginService loginService = new SysLoginService();
            LoginUser loginUser = new LoginUser();
            loginUser.setUserId(sysUser.getUserId());
            loginUser.setUser(sysUser);

            // 生成标准token（与用户名密码登录一致）
            String token = tokenService.createToken(loginUser);
            Map<String, Object> data = new HashMap<>();
            data.put("isBind", true);
            data.put("token", token);
            data.put("expire", Constants.TOKEN_EXPIRE_TIME / 1000);
            data.put("userInfo", sysUser);
            return AjaxResult.success(data);
        } catch (Exception e) {
            return AjaxResult.error("微信登录失败：" + e.getMessage());
        }
    }

    /**
     * 3. 微信OpenID绑定账号（根据身份证号/手机号绑定）
     */
    @ApiOperation("OpenID绑定报名账号")
    @GetMapping("/bind")
    public AjaxResult bind(@RequestParam String openId, @RequestParam String idCard, @RequestParam String phone) {
        // 校验Redis中的OpenID有效性
        String cacheOpenId = redisCache.getCacheObject(Constants.WX_OPENID_KEY + openId);
        if (!openId.equals(cacheOpenId)) {
            return AjaxResult.error("授权已过期，请重新登录");
        }

        // 校验报名信息
        StudentApply apply = studentApplyService.selectStudentApplyByIdCardAndPhone(idCard, phone);
        if (apply == null) {
            return AjaxResult.error("报名信息不存在，请确认身份证号和手机号");
        }

        // 绑定OpenID并生成用户（若未生成）
        sysUserService.bindWxOpenid(apply, openId);
        // 重新生成token
        SysUser sysUser = sysUserService.selectUserById(Long.parseLong(apply.getUserId()));
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setUser(sysUser);
        String token = tokenService.createToken(loginUser);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("expire", Constants.TOKEN_EXPIRE_TIME / 1000);
        data.put("userInfo", sysUser);
        // 删除Redis中的OpenID
        redisCache.deleteObject(Constants.WX_OPENID_KEY + openId);
        return AjaxResult.success("绑定成功，登录成功", data);
    }
}