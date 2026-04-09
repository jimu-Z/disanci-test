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
import com.ruoyi.system.domain.StudentResume;
import com.ruoyi.system.service.IStudentResumeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生履历Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/student_resume")
public class StudentResumeController extends BaseController
{
    @Autowired
    private IStudentResumeService studentResumeService;

    /**
     * 查询学生履历列表
     */
    @PreAuthorize("@ss.hasPermi('system:student_resume:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentResume studentResume)
    {
        startPage();
        List<StudentResume> list = studentResumeService.selectStudentResumeList(studentResume);
        return getDataTable(list);
    }

    /**
     * 导出学生履历列表
     */
    @PreAuthorize("@ss.hasPermi('system:student_resume:export')")
    @Log(title = "学生履历", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentResume studentResume)
    {
        List<StudentResume> list = studentResumeService.selectStudentResumeList(studentResume);
        ExcelUtil<StudentResume> util = new ExcelUtil<StudentResume>(StudentResume.class);
        util.exportExcel(response, list, "学生履历数据");
    }

    /**
     * 获取学生履历详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student_resume:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentResumeService.selectStudentResumeById(id));
    }

    /**
     * 新增学生履历
     */
    @PreAuthorize("@ss.hasPermi('system:student_resume:add')")
    @Log(title = "学生履历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentResume studentResume)
    {
        Long userId = getLoginUser().getUser().getUserId();
        studentResume.setUserId(userId);
        return toAjax(studentResumeService.insertStudentResume(studentResume));
    }

    /**
     * 修改学生履历
     */
    @PreAuthorize("@ss.hasPermi('system:student_resume:edit')")
    @Log(title = "学生履历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentResume studentResume)
    {
        Long userId = getLoginUser().getUser().getUserId();
        studentResume.setUserId(userId);
        return toAjax(studentResumeService.updateStudentResume(studentResume));
    }
    @PreAuthorize("@ss.hasPermi('system:student_resume:list')")
    @GetMapping("/myResume")
    public List<StudentResume> myResume(StudentResume studentResume)
    {
        Long userId = getLoginUser().getUser().getUserId();
        studentResume.setUserId(userId);
          return studentResumeService.selectStudentResumeList(studentResume);

    }
    /**
     * 删除学生履历
     */
    @PreAuthorize("@ss.hasPermi('system:student_resume:remove')")
    @Log(title = "学生履历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {

        return toAjax(studentResumeService.deleteStudentResumeByIds(ids));
    }
}
