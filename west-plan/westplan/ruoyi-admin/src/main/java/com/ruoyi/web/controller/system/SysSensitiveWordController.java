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
import com.ruoyi.system.domain.SysSensitiveWord;
import com.ruoyi.system.service.ISysSensitiveWordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 敏感词库Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/sys_sensitive_word")
public class SysSensitiveWordController extends BaseController
{
    @Autowired
    private ISysSensitiveWordService sysSensitiveWordService;

    /**
     * 查询敏感词库列表
     */
    @PreAuthorize("@ss.hasPermi('system:sys_sensitive_word:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSensitiveWord sysSensitiveWord)
    {
        startPage();
        List<SysSensitiveWord> list = sysSensitiveWordService.selectSysSensitiveWordList(sysSensitiveWord);
        return getDataTable(list);
    }

    /**
     * 导出敏感词库列表
     */
    @PreAuthorize("@ss.hasPermi('system:sys_sensitive_word:export')")
    @Log(title = "敏感词库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSensitiveWord sysSensitiveWord)
    {
        List<SysSensitiveWord> list = sysSensitiveWordService.selectSysSensitiveWordList(sysSensitiveWord);
        ExcelUtil<SysSensitiveWord> util = new ExcelUtil<SysSensitiveWord>(SysSensitiveWord.class);
        util.exportExcel(response, list, "敏感词库数据");
    }

    /**
     * 获取敏感词库详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:sys_sensitive_word:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysSensitiveWordService.selectSysSensitiveWordById(id));
    }

    /**
     * 新增敏感词库
     */
    @PreAuthorize("@ss.hasPermi('system:sys_sensitive_word:add')")
    @Log(title = "敏感词库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSensitiveWord sysSensitiveWord)
    {
        return toAjax(sysSensitiveWordService.insertSysSensitiveWord(sysSensitiveWord));
    }

    /**
     * 修改敏感词库
     */
    @PreAuthorize("@ss.hasPermi('system:sys_sensitive_word:edit')")
    @Log(title = "敏感词库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSensitiveWord sysSensitiveWord)
    {
        return toAjax(sysSensitiveWordService.updateSysSensitiveWord(sysSensitiveWord));
    }

    /**
     * 删除敏感词库
     */
    @PreAuthorize("@ss.hasPermi('system:sys_sensitive_word:remove')")
    @Log(title = "敏感词库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysSensitiveWordService.deleteSysSensitiveWordByIds(ids));
    }
}
