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
import com.ruoyi.system.domain.AlumniFeelingLike;
import com.ruoyi.system.service.IAlumniFeelingLikeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 感悟点赞记录Controller
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
@RestController
@RequestMapping("/system/alumni_feeling_like")
public class AlumniFeelingLikeController extends BaseController
{
    @Autowired
    private IAlumniFeelingLikeService alumniFeelingLikeService;

    /**
     * 查询感悟点赞记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_like:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniFeelingLike alumniFeelingLike)
    {
        startPage();
        List<AlumniFeelingLike> list = alumniFeelingLikeService.selectAlumniFeelingLikeList(alumniFeelingLike);
        return getDataTable(list);
    }

    /**
     * 导出感悟点赞记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_like:export')")
    @Log(title = "感悟点赞记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniFeelingLike alumniFeelingLike)
    {
        List<AlumniFeelingLike> list = alumniFeelingLikeService.selectAlumniFeelingLikeList(alumniFeelingLike);
        ExcelUtil<AlumniFeelingLike> util = new ExcelUtil<AlumniFeelingLike>(AlumniFeelingLike.class);
        util.exportExcel(response, list, "感悟点赞记录数据");
    }

    /**
     * 获取感悟点赞记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_like:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniFeelingLikeService.selectAlumniFeelingLikeById(id));
    }

    /**
     * 新增感悟点赞记录
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_like:add')")
    @Log(title = "感悟点赞记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniFeelingLike alumniFeelingLike)
    {
        return toAjax(alumniFeelingLikeService.insertAlumniFeelingLike(alumniFeelingLike));
    }

    /**
     * 修改感悟点赞记录
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_like:edit')")
    @Log(title = "感悟点赞记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniFeelingLike alumniFeelingLike)
    {
        return toAjax(alumniFeelingLikeService.updateAlumniFeelingLike(alumniFeelingLike));
    }

    /**
     * 删除感悟点赞记录
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling_like:remove')")
    @Log(title = "感悟点赞记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniFeelingLikeService.deleteAlumniFeelingLikeByIds(ids));
    }
}
