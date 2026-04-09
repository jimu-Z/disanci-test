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
import com.ruoyi.system.domain.NewsNoticeUnread;
import com.ruoyi.system.service.INewsNoticeUnreadService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公告未读记录Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/news_notice_unread")
public class NewsNoticeUnreadController extends BaseController
{
    @Autowired
    private INewsNoticeUnreadService newsNoticeUnreadService;

    /**
     * 查询公告未读记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice_unread:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewsNoticeUnread newsNoticeUnread)
    {
        startPage();
        List<NewsNoticeUnread> list = newsNoticeUnreadService.selectNewsNoticeUnreadList(newsNoticeUnread);
        return getDataTable(list);
    }

    /**
     * 导出公告未读记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice_unread:export')")
    @Log(title = "公告未读记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewsNoticeUnread newsNoticeUnread)
    {
        List<NewsNoticeUnread> list = newsNoticeUnreadService.selectNewsNoticeUnreadList(newsNoticeUnread);
        ExcelUtil<NewsNoticeUnread> util = new ExcelUtil<NewsNoticeUnread>(NewsNoticeUnread.class);
        util.exportExcel(response, list, "公告未读记录数据");
    }

    /**
     * 获取公告未读记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice_unread:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(newsNoticeUnreadService.selectNewsNoticeUnreadById(id));
    }

    /**
     * 新增公告未读记录
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice_unread:add')")
    @Log(title = "公告未读记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsNoticeUnread newsNoticeUnread)
    {
        return toAjax(newsNoticeUnreadService.insertNewsNoticeUnread(newsNoticeUnread));
    }

    /**
     * 修改公告未读记录
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice_unread:edit')")
    @Log(title = "公告未读记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsNoticeUnread newsNoticeUnread)
    {
        return toAjax(newsNoticeUnreadService.updateNewsNoticeUnread(newsNoticeUnread));
    }

    /**
     * 删除公告未读记录
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice_unread:remove')")
    @Log(title = "公告未读记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsNoticeUnreadService.deleteNewsNoticeUnreadByIds(ids));
    }
}
