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
import com.ruoyi.system.domain.WxMpConfig;
import com.ruoyi.system.service.IWxMpConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信公众号配置Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/wx_mp_config")
public class WxMpConfigController extends BaseController
{
    @Autowired
    private IWxMpConfigService wxMpConfigService;

    /**
     * 查询微信公众号配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:wx_mp_config:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxMpConfig wxMpConfig)
    {
        startPage();
        List<WxMpConfig> list = wxMpConfigService.selectWxMpConfigList(wxMpConfig);
        return getDataTable(list);
    }

    /**
     * 导出微信公众号配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:wx_mp_config:export')")
    @Log(title = "微信公众号配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxMpConfig wxMpConfig)
    {
        List<WxMpConfig> list = wxMpConfigService.selectWxMpConfigList(wxMpConfig);
        ExcelUtil<WxMpConfig> util = new ExcelUtil<WxMpConfig>(WxMpConfig.class);
        util.exportExcel(response, list, "微信公众号配置数据");
    }

    /**
     * 获取微信公众号配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:wx_mp_config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wxMpConfigService.selectWxMpConfigById(id));
    }

    /**
     * 新增微信公众号配置
     */
    @PreAuthorize("@ss.hasPermi('system:wx_mp_config:add')")
    @Log(title = "微信公众号配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxMpConfig wxMpConfig)
    {
        return toAjax(wxMpConfigService.insertWxMpConfig(wxMpConfig));
    }

    /**
     * 修改微信公众号配置
     */
    @PreAuthorize("@ss.hasPermi('system:wx_mp_config:edit')")
    @Log(title = "微信公众号配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxMpConfig wxMpConfig)
    {
        return toAjax(wxMpConfigService.updateWxMpConfig(wxMpConfig));
    }

    /**
     * 删除微信公众号配置
     */
    @PreAuthorize("@ss.hasPermi('system:wx_mp_config:remove')")
    @Log(title = "微信公众号配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wxMpConfigService.deleteWxMpConfigByIds(ids));
    }
}
