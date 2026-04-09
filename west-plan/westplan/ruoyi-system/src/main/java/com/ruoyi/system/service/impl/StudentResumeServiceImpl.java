package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StudentResumeMapper;
import com.ruoyi.system.domain.StudentResume;
import com.ruoyi.system.service.IStudentResumeService;

/**
 * 学生履历Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class StudentResumeServiceImpl implements IStudentResumeService 
{
    @Autowired
    private StudentResumeMapper studentResumeMapper;

    /**
     * 查询学生履历
     * 
     * @param id 学生履历主键
     * @return 学生履历
     */
    @Override
    public StudentResume selectStudentResumeById(Long id)
    {
        return studentResumeMapper.selectStudentResumeById(id);
    }

    /**
     * 查询学生履历列表
     * 
     * @param studentResume 学生履历
     * @return 学生履历
     */
    @Override
    public List<StudentResume> selectStudentResumeList(StudentResume studentResume)
    {
        return studentResumeMapper.selectStudentResumeList(studentResume);
    }

    /**
     * 新增学生履历
     * 
     * @param studentResume 学生履历
     * @return 结果
     */
    @Override
    public int insertStudentResume(StudentResume studentResume)
    {
        studentResume.setCreateTime(DateUtils.getNowDate());
        return studentResumeMapper.insertStudentResume(studentResume);
    }

    /**
     * 修改学生履历
     * 
     * @param studentResume 学生履历
     * @return 结果
     */
    @Override
    public int updateStudentResume(StudentResume studentResume)
    {
        studentResume.setUpdateTime(DateUtils.getNowDate());
        return studentResumeMapper.updateStudentResume(studentResume);
    }

    /**
     * 批量删除学生履历
     * 
     * @param ids 需要删除的学生履历主键
     * @return 结果
     */
    @Override
    public int deleteStudentResumeByIds(Long[] ids)
    {
        return studentResumeMapper.deleteStudentResumeByIds(ids);
    }

    /**
     * 删除学生履历信息
     * 
     * @param id 学生履历主键
     * @return 结果
     */
    @Override
    public int deleteStudentResumeById(Long id)
    {
        return studentResumeMapper.deleteStudentResumeById(id);
    }
}
