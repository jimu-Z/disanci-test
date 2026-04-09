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
import com.ruoyi.system.domain.SysFriendLink;
import com.ruoyi.system.service.ISysFriendLinkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 友情链接Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/sys_friend_link")
public class SysFriendLinkController extends BaseController
{
    @Autowired
    private ISysFriendLinkService sysFriendLinkService;

    /**
     * 查询友情链接列表
     */
    @PreAuthorize("@ss.hasPermi('system:sys_friend_link:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFriendLink sysFriendLink)
    {
        startPage();
        List<SysFriendLink> list = sysFriendLinkService.selectSysFriendLinkList(sysFriendLink);
        return getDataTable(list);
    }

    /**
     * 导出友情链接列表
     */
    @PreAuthorize("@ss.hasPermi('system:sys_friend_link:export')")
    @Log(title = "友情链接", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFriendLink sysFriendLink)
    {
        List<SysFriendLink> list = sysFriendLinkService.selectSysFriendLinkList(sysFriendLink);
        ExcelUtil<SysFriendLink> util = new ExcelUtil<SysFriendLink>(SysFriendLink.class);
        util.exportExcel(response, list, "友情链接数据");
    }

    /**
     * 获取友情链接详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:sys_friend_link:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysFriendLinkService.selectSysFriendLinkById(id));
    }

    /**
     * 新增友情链接
     */
    @PreAuthorize("@ss.hasPermi('system:sys_friend_link:add')")
    @Log(title = "友情链接", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysFriendLink sysFriendLink)
    {
        return toAjax(sysFriendLinkService.insertSysFriendLink(sysFriendLink));
    }

    /**
     * 修改友情链接
     */
    @PreAuthorize("@ss.hasPermi('system:sys_friend_link:edit')")
    @Log(title = "友情链接", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysFriendLink sysFriendLink)
    {
        return toAjax(sysFriendLinkService.updateSysFriendLink(sysFriendLink));
    }

    /**
     * 删除友情链接
     */
    @PreAuthorize("@ss.hasPermi('system:sys_friend_link:remove')")
    @Log(title = "友情链接", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysFriendLinkService.deleteSysFriendLinkByIds(ids));
    }
}
