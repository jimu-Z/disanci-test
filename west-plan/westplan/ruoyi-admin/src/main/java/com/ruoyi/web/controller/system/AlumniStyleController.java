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
import com.ruoyi.system.domain.AlumniStyle;
import com.ruoyi.system.service.IAlumniStyleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 校友个人风采Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/alumni_style")
public class AlumniStyleController extends BaseController
{
    @Autowired
    private IAlumniStyleService alumniStyleService;

    /**
     * 查询校友个人风采列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_style:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniStyle alumniStyle)
    {
        startPage();
        List<AlumniStyle> list = alumniStyleService.selectAlumniStyleList(alumniStyle);
        return getDataTable(list);
    }

    /**
     * 导出校友个人风采列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_style:export')")
    @Log(title = "校友个人风采", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniStyle alumniStyle)
    {
        List<AlumniStyle> list = alumniStyleService.selectAlumniStyleList(alumniStyle);
        ExcelUtil<AlumniStyle> util = new ExcelUtil<AlumniStyle>(AlumniStyle.class);
        util.exportExcel(response, list, "校友个人风采数据");
    }

    /**
     * 获取校友个人风采详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_style:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniStyleService.selectAlumniStyleById(id));
    }

    /**
     * 新增校友个人风采
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_style:add')")
    @Log(title = "校友个人风采", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniStyle alumniStyle)
    {
        return toAjax(alumniStyleService.insertAlumniStyle(alumniStyle));
    }

    /**
     * 修改校友个人风采
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_style:edit')")
    @Log(title = "校友个人风采", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniStyle alumniStyle)
    {
        return toAjax(alumniStyleService.updateAlumniStyle(alumniStyle));
    }

    /**
     * 删除校友个人风采
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_style:remove')")
    @Log(title = "校友个人风采", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniStyleService.deleteAlumniStyleByIds(ids));
    }
}
