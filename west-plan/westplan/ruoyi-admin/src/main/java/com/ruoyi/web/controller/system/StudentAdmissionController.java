package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.StudentApply;
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
import com.ruoyi.system.domain.StudentAdmission;
import com.ruoyi.system.service.IStudentAdmissionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生录取信息Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/student_admission")
public class StudentAdmissionController extends BaseController
{
    @Autowired
    private IStudentAdmissionService studentAdmissionService;

    /**
     * 查询学生录取信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:student_admission:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentAdmission studentAdmission)
    {
        startPage();
        // 获取当前登录用户ID，存入 params Map 中
        studentAdmission.getParams().put("currentUserId", SecurityUtils.getUserId());
        List<StudentAdmission> list = studentAdmissionService.selectStudentAdmissionList(studentAdmission);
        return getDataTable(list);
    }

    @Log(title = "学生录取导入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:student_admission:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<StudentAdmission> util = new ExcelUtil<StudentAdmission>(StudentAdmission.class);
        List<StudentAdmission> studentList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = studentAdmissionService.importStudent(studentList, updateSupport, operName);
        return success(message);
    }
    @PreAuthorize("@ss.hasPermi('system:student_admission:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<StudentAdmission> util = new ExcelUtil<StudentAdmission>(StudentAdmission.class);
        util.importTemplateExcel(response, "学生录取数据");

    }
    /**
     * 导出学生录取信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:student_admission:export')")
    @Log(title = "学生录取信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentAdmission studentAdmission)
    {
        List<StudentAdmission> list = studentAdmissionService.selectStudentAdmissionList(studentAdmission);
        ExcelUtil<StudentAdmission> util = new ExcelUtil<StudentAdmission>(StudentAdmission.class);
        util.exportExcel(response, list, "学生录取信息数据");
    }

    /**
     * 获取学生录取信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student_admission:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentAdmissionService.selectStudentAdmissionById(id));
    }

    @GetMapping("/count")
    public AjaxResult countStudentAdmission( )
    {
        return success(studentAdmissionService.countStudentAdmission ());
    }

    /**
     * 新增学生录取信息
     */
    @PreAuthorize("@ss.hasPermi('system:student_admission:add')")
    @Log(title = "学生录取信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentAdmission studentAdmission)
    {
        return toAjax(studentAdmissionService.insertStudentAdmission(studentAdmission));
    }

    /**
     * 修改学生录取信息
     */
    @PreAuthorize("@ss.hasPermi('system:student_admission:edit')")
    @Log(title = "学生录取信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentAdmission studentAdmission)
    {
        return toAjax(studentAdmissionService.updateStudentAdmission(studentAdmission));
    }

    /**
     * 删除学生录取信息
     */
    @PreAuthorize("@ss.hasPermi('system:student_admission:remove')")
    @Log(title = "学生录取信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studentAdmissionService.deleteStudentAdmissionByIds(ids));
    }
}
