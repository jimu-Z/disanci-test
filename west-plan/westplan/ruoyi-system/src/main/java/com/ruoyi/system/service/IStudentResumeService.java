package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.StudentResume;

/**
 * 学生履历Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IStudentResumeService 
{
    /**
     * 查询学生履历
     * 
     * @param id 学生履历主键
     * @return 学生履历
     */
    StudentResume selectStudentResumeById(Long id);

    /**
     * 查询学生履历列表
     * 
     * @param studentResume 学生履历
     * @return 学生履历集合
     */
    List<StudentResume> selectStudentResumeList(StudentResume studentResume);

    /**
     * 新增学生履历
     * 
     * @param studentResume 学生履历
     * @return 结果
     */
    int insertStudentResume(StudentResume studentResume);

    /**
     * 修改学生履历
     * 
     * @param studentResume 学生履历
     * @return 结果
     */
    int updateStudentResume(StudentResume studentResume);

    /**
     * 批量删除学生履历
     * 
     * @param ids 需要删除的学生履历主键集合
     * @return 结果
     */
    int deleteStudentResumeByIds(Long[] ids);

    /**
     * 删除学生履历信息
     * 
     * @param id 学生履历主键
     * @return 结果
     */
    int deleteStudentResumeById(Long id);
}
