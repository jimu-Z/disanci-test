package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.system.domain.AlumniFeelingComment;
import com.ruoyi.system.domain.AlumniFeelingReply;
import com.ruoyi.system.domain.AlumniHonor;
import com.ruoyi.system.domain.dto.FeelingWithUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.AlumniFeeling;
import com.ruoyi.system.service.IAlumniFeelingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 校友工作感悟Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/alumni_feeling")
public class AlumniFeelingController extends BaseController
{
    @Autowired
    private IAlumniFeelingService alumniFeelingService;

    /**
     * 查询校友工作感悟列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniFeeling alumniFeeling)
    {
        startPage();
        List<AlumniFeeling> list = alumniFeelingService.selectAlumniFeelingList(alumniFeeling);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:list')")
    @GetMapping("/listWithUser")
    public TableDataInfo listWithUser(AlumniFeeling alumniFeeling)
    {
        startPage();
        List<FeelingWithUser> list = alumniFeelingService.selectFeelingwithUserList(alumniFeeling);
        return getDataTable(list);
    }
    /**
     * 分页查询感悟列表

     * @param userId 当前用户ID（用于判断点赞状态）
     */

    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:list')")
    @GetMapping("/listFeeling")
    public  AjaxResult getFeelingList(
            AlumniFeeling alumniFeeling,
            @RequestParam(required = false) Long userId) {
        Map<String, Object> stringObjectMap = alumniFeelingService.selectAlumniFeelingList(alumniFeeling, userId);
        return success(stringObjectMap);
    }
    /**我的个人感受*/
    @GetMapping("/myFeeling")
    public List<AlumniFeeling> myFeeling(AlumniFeeling alumniFeeling)
    {
        Long userId = getLoginUser().getUser().getUserId();
        alumniFeeling.setUserId(userId);
        return alumniFeelingService.selectAlumniFeelingList(alumniFeeling);

    }
    /**
     * 发布评论
     */
    @PostMapping("/comment")
    public AjaxResult  addComment(@RequestBody AlumniFeelingComment comment) {
        return toAjax(alumniFeelingService.addComment(comment));
    }

    /**
     * 发布回复
     */
    @PostMapping("/reply")
    public AjaxResult  addReply(@RequestBody AlumniFeelingReply reply) {
        return toAjax(alumniFeelingService.addReply(reply));
    }
    /**
     * 导出校友工作感悟列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:export')")
    @Log(title = "校友工作感悟", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniFeeling alumniFeeling)
    {
        List<AlumniFeeling> list = alumniFeelingService.selectAlumniFeelingList(alumniFeeling);
        ExcelUtil<AlumniFeeling> util = new ExcelUtil<AlumniFeeling>(AlumniFeeling.class);
        util.exportExcel(response, list, "校友工作感悟数据");
    }
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:upload')")
    @PostMapping("/uploadImage")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            String path=RuoYiConfig.getPicturePath()+"/"+getUserId();
            String url = FileUploadUtils.upload(path, file, MimeTypeUtils.IMAGE_EXTENSION, false);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);

            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
    /**
     * 获取校友工作感悟详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniFeelingService.selectAlumniFeelingById(id));
    }

    /**
     * 新增校友工作感悟
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:add')")
    @Log(title = "校友工作感悟", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniFeeling alumniFeeling)
    {
        return toAjax(alumniFeelingService.insertAlumniFeeling(alumniFeeling));
    }

    /**
     * 修改校友工作感悟
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:edit')")
    @Log(title = "校友工作感悟", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniFeeling alumniFeeling)
    {
        return toAjax(alumniFeelingService.updateAlumniFeeling(alumniFeeling));
    }

    /**
     * 删除校友工作感悟
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_feeling:remove')")
    @Log(title = "校友工作感悟", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniFeelingService.deleteAlumniFeelingByIds(ids));
    }

}
