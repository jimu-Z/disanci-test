package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.StudentApply;
import com.ruoyi.system.service.IStudentApplyService;
import com.ruoyi.system.service.ISysUserService;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class WxLoginControllerTest {

    private WxLoginController controller;
    private WxMpService wxMpService;
    private ISysUserService sysUserService;
    private IStudentApplyService studentApplyService;
    private TokenService tokenService;
    private RedisCache redisCache;

    @Before
    public void setUp() throws Exception {
        controller = new WxLoginController();
        wxMpService = Mockito.mock(WxMpService.class, Mockito.RETURNS_DEEP_STUBS);
        sysUserService = Mockito.mock(ISysUserService.class);
        studentApplyService = Mockito.mock(IStudentApplyService.class);
        tokenService = Mockito.mock(TokenService.class);
        redisCache = Mockito.mock(RedisCache.class);
        inject(controller, "wxMpService", wxMpService);
        inject(controller, "sysUserService", sysUserService);
        inject(controller, "studentApplyService", studentApplyService);
        inject(controller, "tokenService", tokenService);
        inject(controller, "redisCache", redisCache);
        inject(controller, "loginRedirectUrl", "http://localhost/callback");
    }

    @Test
    public void callback_shouldRejectDisabledUser() throws Exception {
        WxOAuth2AccessToken accessToken = Mockito.mock(WxOAuth2AccessToken.class);
        Mockito.when(accessToken.getOpenId()).thenReturn("openid-1");
        Mockito.when(wxMpService.getOAuth2Service().getAccessToken("code-1")).thenReturn(accessToken);

        StudentApply apply = new StudentApply();
        apply.setUserId("2");
        Mockito.when(studentApplyService.selectStudentApplyByWxOpenid("openid-1")).thenReturn(apply);

        SysUser user = new SysUser();
        user.setUserId(2L);
        user.setStatus("0");
        Mockito.when(sysUserService.selectUserById(2L)).thenReturn(user);

        AjaxResult result = controller.callback("code-1");

        Assert.assertEquals(500, result.get("code"));
        Assert.assertTrue(String.valueOf(result.get("msg")).contains("禁用"));
    }

    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
