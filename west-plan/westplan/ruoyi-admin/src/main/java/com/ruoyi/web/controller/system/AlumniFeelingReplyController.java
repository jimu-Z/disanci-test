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
import com.ruoyi.system.domain.AlumniFeelingReply;
import com.ruoyi.system.service.IAlumniFeelingReplyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评论回复Controller
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
@RestController
@RequestMapping("/system/alumni_feeling_reply")
public class AlumniFeelingReplyController extends BaseController
{
    @Autowired
    private IAlumniFeelingReplyService alumniFeelingReplyService;

    /**
     * 查询评论回复列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_reply:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniFeelingReply alumniFeelingReply)
    {
        startPage();
        List<AlumniFeelingReply> list = alumniFeelingReplyService.selectAlumniFeelingReplyList(alumniFeelingReply);
        return getDataTable(list);
    }

    /**
     * 导出评论回复列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_reply:export')")
    @Log(title = "评论回复", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniFeelingReply alumniFeelingReply)
    {
        List<AlumniFeelingReply> list = alumniFeelingReplyService.selectAlumniFeelingReplyList(alumniFeelingReply);
        ExcelUtil<AlumniFeelingReply> util = new ExcelUtil<AlumniFeelingReply>(AlumniFeelingReply.class);
        util.exportExcel(response, list, "评论回复数据");
    }

    /**
     * 获取评论回复详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_reply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniFeelingReplyService.selectAlumniFeelingReplyById(id));
    }

    /**
     * 新增评论回复
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_reply:add')")
    @Log(title = "评论回复", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniFeelingReply alumniFeelingReply)
    {
        return toAjax(alumniFeelingReplyService.insertAlumniFeelingReply(alumniFeelingReply));
    }

    /**
     * 修改评论回复
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_reply:edit')")
    @Log(title = "评论回复", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniFeelingReply alumniFeelingReply)
    {
        return toAjax(alumniFeelingReplyService.updateAlumniFeelingReply(alumniFeelingReply));
    }

    /**
     * 删除评论回复
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_reply:remove')")
    @Log(title = "评论回复", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniFeelingReplyService.deleteAlumniFeelingReplyByIds(ids));
    }
}
