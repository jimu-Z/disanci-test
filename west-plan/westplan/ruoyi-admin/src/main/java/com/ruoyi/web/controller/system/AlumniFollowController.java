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
import com.ruoyi.system.domain.AlumniFollow;
import com.ruoyi.system.service.IAlumniFollowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 校友关注Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/alumni_follow")
public class AlumniFollowController extends BaseController
{
    @Autowired
    private IAlumniFollowService alumniFollowService;

    /**
     * 查询校友关注列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_follow:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniFollow alumniFollow)
    {
        startPage();
        List<AlumniFollow> list = alumniFollowService.selectAlumniFollowList(alumniFollow);
        return getDataTable(list);
    }

    /**
     * 导出校友关注列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_follow:export')")
    @Log(title = "校友关注", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniFollow alumniFollow)
    {
        List<AlumniFollow> list = alumniFollowService.selectAlumniFollowList(alumniFollow);
        ExcelUtil<AlumniFollow> util = new ExcelUtil<AlumniFollow>(AlumniFollow.class);
        util.exportExcel(response, list, "校友关注数据");
    }

    /**
     * 获取校友关注详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_follow:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniFollowService.selectAlumniFollowById(id));
    }

    /**
     * 新增校友关注
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_follow:add')")
    @Log(title = "校友关注", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniFollow alumniFollow)
    {
        return toAjax(alumniFollowService.insertAlumniFollow(alumniFollow));
    }

    /**
     * 修改校友关注
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_follow:edit')")
    @Log(title = "校友关注", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniFollow alumniFollow)
    {
        return toAjax(alumniFollowService.updateAlumniFollow(alumniFollow));
    }

    /**
     * 删除校友关注
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_follow:remove')")
    @Log(title = "校友关注", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniFollowService.deleteAlumniFollowByIds(ids));
    }
}
