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
import com.ruoyi.system.domain.NewsNotice;
import com.ruoyi.system.service.INewsNoticeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻公告Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/news_notice")
public class NewsNoticeController extends BaseController
{
    @Autowired
    private INewsNoticeService newsNoticeService;

    /**
     * 查询新闻公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewsNotice newsNotice)
    {
        startPage();
        List<NewsNotice> list = newsNoticeService.selectNewsNoticeList(newsNotice);
        return getDataTable(list);
    }

    /**
     * 导出新闻公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice:export')")
    @Log(title = "新闻公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewsNotice newsNotice)
    {
        List<NewsNotice> list = newsNoticeService.selectNewsNoticeList(newsNotice);
        ExcelUtil<NewsNotice> util = new ExcelUtil<NewsNotice>(NewsNotice.class);
        util.exportExcel(response, list, "新闻公告数据");
    }

    /**
     * 获取新闻公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(newsNoticeService.selectNewsNoticeById(id));
    }

    /**
     * 新增新闻公告
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice:add')")
    @Log(title = "新闻公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewsNotice newsNotice)
    {
        return toAjax(newsNoticeService.insertNewsNotice(newsNotice));
    }

    /**
     * 修改新闻公告
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice:edit')")
    @Log(title = "新闻公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewsNotice newsNotice)
    {
        return toAjax(newsNoticeService.updateNewsNotice(newsNotice));
    }

    /**
     * 删除新闻公告
     */
    @PreAuthorize("@ss.hasPermi('system:news_notice:remove')")
    @Log(title = "新闻公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newsNoticeService.deleteNewsNoticeByIds(ids));
    }
}
