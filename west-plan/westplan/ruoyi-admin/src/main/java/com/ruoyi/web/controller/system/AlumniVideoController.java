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
import com.ruoyi.system.domain.AlumniVideo;
import com.ruoyi.system.service.IAlumniVideoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 校友视频Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/alumni_video")
public class AlumniVideoController extends BaseController
{
    @Autowired
    private IAlumniVideoService alumniVideoService;

    /**
     * 查询校友视频列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_video:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniVideo alumniVideo)
    {
        startPage();
        List<AlumniVideo> list = alumniVideoService.selectAlumniVideoList(alumniVideo);
        return getDataTable(list);
    }

    /**
     * 导出校友视频列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_video:export')")
    @Log(title = "校友视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniVideo alumniVideo)
    {
        List<AlumniVideo> list = alumniVideoService.selectAlumniVideoList(alumniVideo);
        ExcelUtil<AlumniVideo> util = new ExcelUtil<AlumniVideo>(AlumniVideo.class);
        util.exportExcel(response, list, "校友视频数据");
    }

    /**
     * 获取校友视频详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_video:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniVideoService.selectAlumniVideoById(id));
    }

    /**
     * 新增校友视频
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_video:add')")
    @Log(title = "校友视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniVideo alumniVideo)
    {
        return toAjax(alumniVideoService.insertAlumniVideo(alumniVideo));
    }

    /**
     * 修改校友视频
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_video:edit')")
    @Log(title = "校友视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniVideo alumniVideo)
    {
        return toAjax(alumniVideoService.updateAlumniVideo(alumniVideo));
    }

    /**
     * 删除校友视频
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_video:remove')")
    @Log(title = "校友视频", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniVideoService.deleteAlumniVideoByIds(ids));
    }
}
