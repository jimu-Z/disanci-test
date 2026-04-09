package com.ruoyi.web.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.WpWxUser;
import com.ruoyi.system.service.IWpWxUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户绑定Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/wp_wx_user")
public class WpWxUserController extends BaseController
{
    @Autowired
    private IWpWxUserService wpWxUserService;

    /**
     * 查询微信用户绑定列表
     */
    @PreAuthorize("@ss.hasPermi('system:wp_wx_user:list')")
    @GetMapping("/list")
    public TableDataInfo list(WpWxUser wpWxUser)
    {
        startPage();
        List<WpWxUser> list = wpWxUserService.selectWpWxUserList(wpWxUser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户绑定列表
     */
    @PreAuthorize("@ss.hasPermi('system:wp_wx_user:export')")
    @Log(title = "微信用户绑定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WpWxUser wpWxUser)
    {
        List<WpWxUser> list = wpWxUserService.selectWpWxUserList(wpWxUser);
        ExcelUtil<WpWxUser> util = new ExcelUtil<WpWxUser>(WpWxUser.class);
        util.exportExcel(response, list, "微信用户绑定数据");
    }

    /**
     * 获取微信用户绑定详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:wp_wx_user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wpWxUserService.selectWpWxUserById(id));
    }

    /**
     * 新增微信用户绑定
     */
    @PreAuthorize("@ss.hasPermi('system:wp_wx_user:add')")
    @Log(title = "微信用户绑定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WpWxUser wpWxUser)
    {
        return toAjax(wpWxUserService.insertWpWxUser(wpWxUser));
    }

    /**
     * 修改微信用户绑定
     */
    @PreAuthorize("@ss.hasPermi('system:wp_wx_user:edit')")
    @Log(title = "微信用户绑定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WpWxUser wpWxUser)
    {
        return toAjax(wpWxUserService.updateWpWxUser(wpWxUser));
    }

    /**
     * 删除微信用户绑定
     */
    @PreAuthorize("@ss.hasPermi('system:wp_wx_user:remove')")
    @Log(title = "微信用户绑定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wpWxUserService.deleteWpWxUserByIds(ids));
    }
}
