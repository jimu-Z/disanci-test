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
import com.ruoyi.system.domain.CommonInteract;
import com.ruoyi.system.service.ICommonInteractService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 通用点赞/收藏Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/common_interact")
public class CommonInteractController extends BaseController
{
    @Autowired
    private ICommonInteractService commonInteractService;

    /**
     * 查询通用点赞/收藏列表
     */
    @PreAuthorize("@ss.hasPermi('system:common_interact:list')")
    @GetMapping("/list")
    public TableDataInfo list(CommonInteract commonInteract)
    {
        startPage();
        List<CommonInteract> list = commonInteractService.selectCommonInteractList(commonInteract);
        return getDataTable(list);
    }

    /**
     * 导出通用点赞/收藏列表
     */
    @PreAuthorize("@ss.hasPermi('system:common_interact:export')")
    @Log(title = "通用点赞/收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommonInteract commonInteract)
    {
        List<CommonInteract> list = commonInteractService.selectCommonInteractList(commonInteract);
        ExcelUtil<CommonInteract> util = new ExcelUtil<CommonInteract>(CommonInteract.class);
        util.exportExcel(response, list, "通用点赞/收藏数据");
    }

    /**
     * 获取通用点赞/收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:common_interact:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(commonInteractService.selectCommonInteractById(id));
    }

    /**
     * 新增通用点赞/收藏
     */
    @PreAuthorize("@ss.hasPermi('system:common_interact:add')")
    @Log(title = "通用点赞/收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommonInteract commonInteract)
    {
        return toAjax(commonInteractService.insertCommonInteract(commonInteract));
    }

    /**
     * 修改通用点赞/收藏
     */
    @PreAuthorize("@ss.hasPermi('system:common_interact:edit')")
    @Log(title = "通用点赞/收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommonInteract commonInteract)
    {
        return toAjax(commonInteractService.updateCommonInteract(commonInteract));
    }

    /**
     * 删除通用点赞/收藏
     */
    @PreAuthorize("@ss.hasPermi('system:common_interact:remove')")
    @Log(title = "通用点赞/收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(commonInteractService.deleteCommonInteractByIds(ids));
    }
}
