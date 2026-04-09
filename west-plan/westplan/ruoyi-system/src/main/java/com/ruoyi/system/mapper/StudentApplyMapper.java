package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.StudentApply;

/**
 * 学生报名Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface StudentApplyMapper 
{
    /**
     * 查询学生报名
     * 
     * @param id 学生报名主键
     * @return 学生报名
     */
    StudentApply selectStudentApplyById(Long id);

    /**
     * 查询学生报名列表
     * 
     * @param studentApply 学生报名
     * @return 学生报名集合
     */
    List<StudentApply> selectStudentApplyList(StudentApply studentApply);

    /**
     * 新增学生报名
     * 
     * @param studentApply 学生报名
     * @return 结果
     */
    int insertStudentApply(StudentApply studentApply);

    /**
     * 修改学生报名
     * 
     * @param studentApply 学生报名
     * @return 结果
     */
    int updateStudentApply(StudentApply studentApply);

    /**
     * 删除学生报名
     * 
     * @param id 学生报名主键
     * @return 结果
     */
    int deleteStudentApplyById(Long id);

    /**
     * 批量删除学生报名
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteStudentApplyByIds(Long[] ids);

    StudentApply selectStudentApplyByUserId(Long userId);
}
