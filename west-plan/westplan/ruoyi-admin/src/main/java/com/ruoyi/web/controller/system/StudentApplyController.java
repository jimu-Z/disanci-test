package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
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
import com.ruoyi.system.domain.StudentApply;
import com.ruoyi.system.service.IStudentApplyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学生报名Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/student_apply")
public class StudentApplyController extends BaseController
{
    @Autowired
    private IStudentApplyService studentApplyService;

    /**
     * 查询学生报名列表
     */
    @PreAuthorize("@ss.hasPermi('system:student_apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudentApply studentApply)
    {
        startPage();
        List<StudentApply> list = studentApplyService.selectStudentApplyList(studentApply);
        return getDataTable(list);
    }

    @Log(title = "学生报名管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:student_apply:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<StudentApply> util = new ExcelUtil<StudentApply>(StudentApply.class);
        List<StudentApply> studentList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = studentApplyService.importStudent(studentList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<StudentApply> util = new ExcelUtil<StudentApply>(StudentApply.class);
        util.importTemplateExcel(response, "学生数据");
    }
    /**
     * 导出学生报名列表
     */
    @PreAuthorize("@ss.hasPermi('system:student_apply:export')")
    @Log(title = "学生报名", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudentApply studentApply)
    {
        List<StudentApply> list = studentApplyService.selectStudentApplyList(studentApply);
        ExcelUtil<StudentApply> util = new ExcelUtil<StudentApply>(StudentApply.class);
        util.exportExcel(response, list, "学生报名数据");
    }

    /**
     * 获取学生报名详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student_apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(studentApplyService.selectStudentApplyById(id));
    }
    /**
     * 获取学生报名详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:student_apply:query')")
    @GetMapping( "/myinfo")
    public AjaxResult myinfo( )
    {
        //获取当前用户id
        Long uid = getUserId();
        if(uid==1){
            StudentApply studentApply =new StudentApply();
            studentApply.setUserId("1");
            studentApply.setUserName("管理员");
            return success(studentApply);
        }else {
            StudentApply studentApply =new StudentApply();
            studentApply.setUserId(uid.toString());
         List<StudentApply>  list=  studentApplyService.selectStudentApplyList(studentApply) ;
         if(list.size()>0)
             return success(list.get(0));
         else
             return error("无此用户");
        }



    }

    /**
     * 新增学生报名
     */
    @PreAuthorize("@ss.hasPermi('system:student_apply:add')")
    @Log(title = "学生报名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudentApply studentApply)
    {
        return toAjax(studentApplyService.insertStudentApply(studentApply));
    }

    /**
     * 修改学生报名
     */
    @PreAuthorize("@ss.hasPermi('system:student_apply:edit')")
    @Log(title = "学生报名", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudentApply studentApply)
    {
        return toAjax(studentApplyService.updateStudentApply(studentApply));
    }

    /**
     * 删除学生报名
     */
    @PreAuthorize("@ss.hasPermi('system:student_apply:remove')")
    @Log(title = "学生报名", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studentApplyService.deleteStudentApplyByIds(ids));
    }
}
