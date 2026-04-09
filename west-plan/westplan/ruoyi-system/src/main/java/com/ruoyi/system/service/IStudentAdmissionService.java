package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.StudentAdmission;

/**
 * 学生录取信息Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IStudentAdmissionService 
{
    /**
     * 查询学生录取信息
     * 
     * @param id 学生录取信息主键
     * @return 学生录取信息
     */
    StudentAdmission selectStudentAdmissionById(Long id);

    /**
     * 查询学生录取信息列表
     * 
     * @param studentAdmission 学生录取信息
     * @return 学生录取信息集合
     */
    List<StudentAdmission> selectStudentAdmissionList(StudentAdmission studentAdmission);

    /**
     * 新增学生录取信息
     * 
     * @param studentAdmission 学生录取信息
     * @return 结果
     */
    int insertStudentAdmission(StudentAdmission studentAdmission);

    /**
     * 修改学生录取信息
     * 
     * @param studentAdmission 学生录取信息
     * @return 结果
     */
    int updateStudentAdmission(StudentAdmission studentAdmission);

    /**
     * 批量删除学生录取信息
     * 
     * @param ids 需要删除的学生录取信息主键集合
     * @return 结果
     */
    int deleteStudentAdmissionByIds(Long[] ids);

    /**
     * 删除学生录取信息信息
     * 
     * @param id 学生录取信息主键
     * @return 结果
     */
    int deleteStudentAdmissionById(Long id);

    String importStudent(List<StudentAdmission> studentList, boolean updateSupport, String operName);

    int countStudentAdmission();
}
