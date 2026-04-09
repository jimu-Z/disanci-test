package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.ContentAudit;
import com.ruoyi.system.service.IContentAuditService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 内容审核记录Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/content_audit")
public class ContentAuditController extends BaseController
{
    @Autowired
    private IContentAuditService contentAuditService;

    /**
     * 查询内容审核记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:content_audit:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContentAudit contentAudit)
    {
        startPage();
        List<ContentAudit> list = contentAuditService.selectContentAuditList(contentAudit);
        return getDataTable(list);
    }

    /**
     * 导出内容审核记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:content_audit:export')")
    @Log(title = "内容审核记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContentAudit contentAudit)
    {
        List<ContentAudit> list = contentAuditService.selectContentAuditList(contentAudit);
        ExcelUtil<ContentAudit> util = new ExcelUtil<ContentAudit>(ContentAudit.class);
        util.exportExcel(response, list, "内容审核记录数据");
    }

    /**
     * 获取内容审核记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:content_audit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(contentAuditService.selectContentAuditById(id));
    }

    /**
     * 新增内容审核记录
     */
    @PreAuthorize("@ss.hasPermi('system:content_audit:add')")
    @Log(title = "内容审核记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContentAudit contentAudit)
    {
        return toAjax(contentAuditService.insertContentAudit(contentAudit));
    }

    /**
     * 修改内容审核记录
     */
    @PreAuthorize("@ss.hasPermi('system:content_audit:edit')")
    @Log(title = "内容审核记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContentAudit contentAudit)
    {
        return toAjax(contentAuditService.updateContentAudit(contentAudit));
    }

    /**
     * 删除内容审核记录
     */
    @PreAuthorize("@ss.hasPermi('system:content_audit:remove')")
    @Log(title = "内容审核记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(contentAuditService.deleteContentAuditByIds(ids));
    }
}
