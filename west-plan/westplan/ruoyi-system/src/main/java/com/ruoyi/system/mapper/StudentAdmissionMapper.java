package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.StudentAdmission;
import com.ruoyi.system.domain.StudentApply;
import org.apache.ibatis.annotations.Param;

/**
 * 学生录取信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface StudentAdmissionMapper 
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
     * 删除学生录取信息
     * 
     * @param id 学生录取信息主键
     * @return 结果
     */
    int deleteStudentAdmissionById(Long id);

    /**
     * 批量删除学生录取信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteStudentAdmissionByIds(Long[] ids);

    StudentAdmission selectStudentAdmissionByUserId(@Param("id") long userId);

    int countStudentAdmission();
}
