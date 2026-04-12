package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StudentAdmissionMapper;
import com.ruoyi.system.domain.StudentAdmission;
import com.ruoyi.system.service.IStudentAdmissionService;

import javax.validation.Validator;

/**
 * 学生录取信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class StudentAdmissionServiceImpl implements IStudentAdmissionService 
{
    @Autowired
    private StudentAdmissionMapper studentAdmissionMapper;
    @Autowired
    protected Validator validator;
    @Autowired
    private SysUserMapper userMapper;


    @Autowired
    private SysUserRoleMapper userRoleMapper;



    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysDeptService deptService;
    private static final Logger log = LoggerFactory.getLogger(StudentAdmissionServiceImpl.class);
    /**
     * 查询学生录取信息
     * 
     * @param id 学生录取信息主键
     * @return 学生录取信息
     */
    @Override
    public StudentAdmission selectStudentAdmissionById(Long id)
    {
        return studentAdmissionMapper.selectStudentAdmissionById(id);
    }

    /**
     * 查询学生录取信息列表
     * 
     * @param studentAdmission 学生录取信息
     * @return 学生录取信息
     */
    @Override
    public List<StudentAdmission> selectStudentAdmissionList(StudentAdmission studentAdmission)
    {
        return studentAdmissionMapper.selectStudentAdmissionList(studentAdmission);
    }

    /**
     * 新增学生录取信息
     * 
     * @param studentAdmission 学生录取信息
     * @return 结果
     */
    @Override
    public int insertStudentAdmission(StudentAdmission studentAdmission)
    {
        studentAdmission.setCreateTime(DateUtils.getNowDate());
        return studentAdmissionMapper.insertStudentAdmission(studentAdmission);
    }

    /**
     * 修改学生录取信息
     * 
     * @param studentAdmission 学生录取信息
     * @return 结果
     */
    @Override
    public int updateStudentAdmission(StudentAdmission studentAdmission)
    {
        studentAdmission.setUpdateTime(DateUtils.getNowDate());
        return studentAdmissionMapper.updateStudentAdmission(studentAdmission);
    }

    /**
     * 批量删除学生录取信息
     * 
     * @param ids 需要删除的学生录取信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentAdmissionByIds(Long[] ids)
    {
        return studentAdmissionMapper.deleteStudentAdmissionByIds(ids);
    }

    /**
     * 删除学生录取信息信息
     * 
     * @param id 学生录取信息主键
     * @return 结果
     */
    @Override
    public int deleteStudentAdmissionById(Long id)
    {
        return studentAdmissionMapper.deleteStudentAdmissionById(id);
    }
    private void insertUser(StudentAdmission student, boolean isUpdateSupport, String operName){
        try{
            if (StringUtils.isBlank(student.getUserId()) || !StringUtils.isNumeric(student.getUserId())) {
                throw new ServiceException("学号必须为数字格式，无法同步系统用户");
            }
            //构建用户信息，插入用户表
            SysUser user = new SysUser();
            user.setUserName(student.getUserId());//学号
            SysDept dept = new SysDept();
            dept.setDeptName(student.getCollege());
            List<SysDept> sysDepts = deptService.selectDeptList(dept);
            if(sysDepts.size()==0){//如果单位不存在建立单位信息
                dept.setParentId(100L);
                dept.setCreateBy(operName);
                dept.setCreateTime(DateUtils.getNowDate());
                deptService.insertDept(dept);
                sysDepts = deptService.selectDeptList(dept);
            }

            user.setDeptId(sysDepts.get(0).getDeptId());
            user.setNickName(student.getUserName());
            user.setStatus("0");
            user.setCreateBy(operName);
            user.setCreateTime(DateUtils.getNowDate());
            // 验证是否存在这个用户
            SysUser u = userMapper.selectUserByUserName(user.getUserName());//根据学号验证是否存在该用户
            if (StringUtils.isNull(u))
            {
                BeanValidators.validateWithException(validator, user);
                deptService.checkDeptDataScope(user.getDeptId());
                String password = configService.selectConfigByKey("sys.user.initPassword");
                user.setPassword(SecurityUtils.encryptPassword(password));
                user.setCreateBy(operName);
                userMapper.insertUser(user);

            }
            else if (isUpdateSupport)
            {
                BeanValidators.validateWithException(validator, user);
                checkUserAllowed(u);
                deptService.checkDeptDataScope(user.getDeptId());
                user.setUserId(u.getUserId());
                user.setDeptId(u.getDeptId());
                user.setUpdateBy(operName);
                userMapper.updateUser(user);

            }
            u=userMapper.selectUserByUserName(user.getUserName());
            if (StringUtils.isNull(u)) {
                throw new ServiceException("系统用户同步失败，请检查学号和学院信息是否有效");
            }
            //给用户分配权限
            insertUserRole(u.getUserId(), new Long[]{Long.valueOf(configService.selectConfigByKey("sys.user.role.alumni"))});

        }catch (Exception e){
            log.error("用户信息插入异常",e);
            if (e instanceof ServiceException) {
                throw (ServiceException) e;
            }
            throw new ServiceException(e.getMessage());
        }

    }
    /**
     * 新增用户角色信息
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        if (StringUtils.isNotEmpty(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    private void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }
    @Override
    public String importStudent(List<StudentAdmission> studentList, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(studentList) || studentList.size() == 0)
        {
            throw new ServiceException("导入录取数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StudentAdmission student : studentList)
        {
            try
            {
                          // 根据学号验证是否存在这个用户
                if (StringUtils.isBlank(student.getUserId()) || !StringUtils.isNumeric(student.getUserId())) {
                    throw new ServiceException("学号必须为数字格式");
                }
                StudentAdmission u = studentAdmissionMapper.selectStudentAdmissionByUserId(Long.parseLong(student.getUserId()));
                if (StringUtils.isNull(u))//没有申请信息
                {
                    BeanValidators.validateWithException(validator, student);
                    student.setCreateTime(DateUtils.getNowDate());
                    student.setUpdateTime(DateUtils.getNowDate());
                    student.setCreateBy(operName);
                    studentAdmissionMapper.insertStudentAdmission(student);

                    insertUser(student, isUpdateSupport, operName);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + student.getUserName() + " 导入成功");
                }
                else if (isUpdateSupport)//存在则更新学生信息
                {
                    student.setId(u.getId());
                    BeanValidators.validateWithException(validator, student);
                    student.setUpdateTime(DateUtils.getNowDate());
                    student.setUpdateBy(operName);
                    studentAdmissionMapper.updateStudentAdmission(student);
                    insertUser(student, isUpdateSupport, operName);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " +student.getUserName()+ " 更新成功");
                    insertUser(u, isUpdateSupport, operName);
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " +student.getUserName()+ " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + student.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public int countStudentAdmission() {
        return studentAdmissionMapper.countStudentAdmission();
    }

}

