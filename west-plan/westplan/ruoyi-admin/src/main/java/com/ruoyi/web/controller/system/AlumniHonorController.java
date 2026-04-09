package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.system.domain.StudentResume;
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
import com.ruoyi.system.domain.AlumniHonor;
import com.ruoyi.system.service.IAlumniHonorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 校友荣誉彰Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/alumni_honor")
public class AlumniHonorController extends BaseController
{
    @Autowired
    private IAlumniHonorService alumniHonorService;

    /**
     * 查询校友荣誉彰列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_honor:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniHonor alumniHonor)
    {
        startPage();
        List<AlumniHonor> list = alumniHonorService.selectAlumniHonorList(alumniHonor);
        return getDataTable(list);
    }

    /**
     * 导出校友荣誉彰列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_honor:export')")
    @Log(title = "校友荣誉彰", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniHonor alumniHonor)
    {
        List<AlumniHonor> list = alumniHonorService.selectAlumniHonorList(alumniHonor);
        ExcelUtil<AlumniHonor> util = new ExcelUtil<AlumniHonor>(AlumniHonor.class);
        util.exportExcel(response, list, "校友荣誉彰数据");
    }

    /**
     * 获取校友荣誉彰详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_honor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniHonorService.selectAlumniHonorById(id));
    }


    @GetMapping("/myHonor")
    public List<AlumniHonor> myHonor(AlumniHonor alumniHonor)
    {
        Long userId = getLoginUser().getUser().getUserId();
        alumniHonor.setUserId(userId);
        return alumniHonorService.selectAlumniHonorList(alumniHonor);

    }
    /**
     * 新增校友荣誉彰
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_honor:add')")
    @Log(title = "校友荣誉彰", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniHonor alumniHonor)
    {
        return toAjax(alumniHonorService.insertAlumniHonor(alumniHonor));
    }

    /**
     * 修改校友荣誉彰
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_honor:edit')")
    @Log(title = "校友荣誉彰", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniHonor alumniHonor)
    {
        return toAjax(alumniHonorService.updateAlumniHonor(alumniHonor));
    }

    /**
     * 删除校友荣誉彰
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_honor:remove')")
    @Log(title = "校友荣誉彰", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniHonorService.deleteAlumniHonorByIds(ids));
    }
    @PreAuthorize("@ss.hasPermi('system:alumni_honor:upload')")
    @PostMapping("/uploadImage")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {

        try
        {
            String path= RuoYiConfig.getPicturePath()+"/"+getUserId();
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
}
