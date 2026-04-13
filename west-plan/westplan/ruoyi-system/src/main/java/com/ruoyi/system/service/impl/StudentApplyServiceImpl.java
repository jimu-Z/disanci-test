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
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.StudentApply;
import com.ruoyi.system.service.IStudentApplyService;

import javax.validation.Validator;

/**
 * 学生报名Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class StudentApplyServiceImpl implements IStudentApplyService 
{
    @Autowired
    private StudentApplyMapper studentApplyMapper;
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
    private static final Logger log = LoggerFactory.getLogger(StudentApplyServiceImpl.class);

    /**
     * 查询学生报名
     * 
     * @param id 学生报名主键
     * @return 学生报名
     */
    @Override
    public StudentApply selectStudentApplyById(Long id)
    {
        return studentApplyMapper.selectStudentApplyById(id);
    }

    /**
     * 查询学生报名列表
     * 
     * @param studentApply 学生报名
     * @return 学生报名
     */
    @Override
    public List<StudentApply> selectStudentApplyList(StudentApply studentApply)
    {
        return studentApplyMapper.selectStudentApplyList(studentApply);
    }

    /**
     * 新增学生报名
     * 
     * @param student 学生报名
     * @return 结果
     */
    @Override
    public int insertStudentApply(StudentApply student )
    {
        if(student.getIntention1Project()!=null)
            student.setIntention1(student.getIntention1Project()+","+student.getIntention1Province()+","+student.getIntention1Special());
        if(student.getIntention2Project()!=null)
            student.setIntention2(student.getIntention2Project()+","+student.getIntention2Province()+","+student.getIntention2Special());
        if(student.getIntention3Project()!=null)
            student.setIntention3(student.getIntention3Project()+","+student.getIntention3Province()+","+student.getIntention3Special());

        student.setCreateTime(DateUtils.getNowDate());
        return studentApplyMapper.insertStudentApply(student);
    }

    /**
     * 修改学生报名
     * 
     * @param student 学生报名
     * @return 结果
     */
    @Override
    public int updateStudentApply(StudentApply student )
    {
        if(student.getIntention1Project()!=null)
            student.setIntention1(student.getIntention1Project()+","+student.getIntention1Province()+","+student.getIntention1Special());
        if(student.getIntention2Project()!=null)
            student.setIntention2(student.getIntention2Project()+","+student.getIntention2Province()+","+student.getIntention2Special());
        if(student.getIntention3Project()!=null)
            student.setIntention3(student.getIntention3Project()+","+student.getIntention3Province()+","+student.getIntention3Special());

        student.setUpdateTime(DateUtils.getNowDate());
        student.setUpdateBy(SecurityUtils.getUsername());
        return studentApplyMapper.updateStudentApply(student);
    }

    /**
     * 批量删除学生报名
     * 
     * @param ids 需要删除的学生报名主键
     * @return 结果
     */
    @Override
    public int deleteStudentApplyByIds(Long[] ids)
    {
        return studentApplyMapper.deleteStudentApplyByIds(ids);
    }

    /**
     * 删除学生报名信息
     * 
     * @param id 学生报名主键
     * @return 结果
     */
    @Override
    public int deleteStudentApplyById(Long id)
    {
        return studentApplyMapper.deleteStudentApplyById(id);
    }

    @Override
    public StudentApply selectStudentApplyByWxOpenid(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        SysUser bindUser = userMapper.selectUserByWxOpenid(openId.trim());
        if (bindUser == null || bindUser.getUserId() == null) {
            return null;
        }
        return studentApplyMapper.selectStudentApplyByUserId(bindUser.getUserId());
    }

    @Override
    public StudentApply selectStudentApplyByIdCardAndPhone(String idCard, String phone) {
        if (StringUtils.isBlank(idCard) || StringUtils.isBlank(phone)) {
            return null;
        }
        StudentApply studentApply = new StudentApply();
        studentApply.setIdCard(idCard);
        studentApply.setPhone(phone);
        List<StudentApply> list = studentApplyMapper.selectStudentApplyList(studentApply);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    private void insertUser(StudentApply student, boolean isUpdateSupport, String operName){
        try{
            //构建用户信息，插入用户表
            SysUser user = new SysUser();
            user.setUserId(Long.parseLong(student.getUserId()) );
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
            //给用户分配权限
            insertUserRole(u.getUserId(), new Long[]{Long.valueOf(configService.selectConfigByKey("sys.user.role.student"))});

        }catch (Exception e){
            log.error("用户信息插入异常",e);
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
        if (StringUtils.isNotEmpty(roleIds))
        {
            userRoleMapper.deleteUserRoleByUserId(userId);
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
/**
 * 导入学生信息
 *
 * @param studentList 学生列表
 * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
 * @return 结果
 */
    @Override
    public String importStudent(List<StudentApply> studentList, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(studentList) || studentList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StudentApply student : studentList)
        {
            try
            {
                if(student.getIntention1Project()!=null)
                    student.setIntention1(student.getIntention1Project()+","+student.getIntention1Province()+","+student.getIntention1Special());
                if(student.getIntention2Project()!=null)
                    student.setIntention2(student.getIntention2Project()+","+student.getIntention2Province()+","+student.getIntention2Special());
                if(student.getIntention3Project()!=null)
                     student.setIntention3(student.getIntention3Project()+","+student.getIntention3Province()+","+student.getIntention3Special());
                // 根据学号验证是否存在这个用户
                StudentApply u = studentApplyMapper.selectStudentApplyByUserId(Long.parseLong(student.getUserId()) );
                if (StringUtils.isNull(u))//没有申请信息
                {
                    BeanValidators.validateWithException(validator, student);
                    student.setCreateTime(DateUtils.getNowDate());
                    student.setUpdateTime(DateUtils.getNowDate());
                    student.setCreateBy(operName);
                    studentApplyMapper.insertStudentApply(student);

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
                    studentApplyMapper.updateStudentApply(student);
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


}
