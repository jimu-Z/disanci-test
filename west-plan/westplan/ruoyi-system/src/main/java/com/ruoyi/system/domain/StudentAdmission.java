package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生录取信息（西部计划/三支一扶等项目）对象 student_admission
 * 
 * @author ruoyi
 * @date 2026-02-10
 */
public class StudentAdmission extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表主键，自增序号 */
    private Long id;

    /** 学号 */
    @Excel(name = "学号")
    private String userId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String userName;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 性别（男/女） */
    @Excel(name = "性别" )
    private String gender;

    /** 民族（如：汉、维吾尔、回等） */
    @Excel(name = "民族" )
    private String nation;

    /** 出生年月（格式：YYYY-MM-DD） */
    @Excel(name = "出生年月" )
    private String birthDate;

    /** 政治面貌（中共党员/共青团员/群众等） */
    @Excel(name = "政治面貌" )
    private String politicalStatus;

    /** 学院/系名称 */
    @Excel(name = "学院/系名称")
    private String college;

    /** 专业全称 */
    @Excel(name = "专业全称")
    private String major;

    /** 学校建行卡号 */
    @Excel(name = "学校建行卡号")
    private String schoolCcbCard;

    /** 籍贯省(区、市) */
    @Excel(name = "籍贯省(区、市)")
    private String nativeProvince;

    /** 籍贯市(地、州) */
    @Excel(name = "籍贯市(地、州)")
    private String nativeCity;

    /** 户籍地省(区、市) */
    @Excel(name = "户籍地省(区、市)")
    private String hukouProvince;

    /** 户籍地市(地、州) */
    @Excel(name = "户籍地市(地、州)")
    private String hukouCity;

    /** 家庭住址（精确到门牌号） */
    @Excel(name = "家庭住址" )
    private String familyAddress;

    /** 身高（单位：米，如1.75） */
    @Excel(name = "身高" )
    private String height;

    /** 志愿者polo衫尺码（如S/M/L/XL/XXL） */
    @Excel(name = "志愿者polo衫尺码" )
    private String poloSize;

    /** 是否参加出征仪式（0=否，1=是） */
    @Excel(name = "是否参加出征仪式" )
    private String attendCeremony;

    /** 分配省份 */
    @Excel(name = "分配省份")
    private String assignProvince;

    /** 是否有教师资格证（0=无，1=有） */
    @Excel(name = "是否有教师资格证" )
    private String hasTeacherCert;

    /** 学段学科（如：高中数学，无则为空字符串） */
    @Excel(name = "学段学科" )
    private String teacherSubject;

    /** 项目类型（如：西部计划、三支一扶等） */
    @Excel(name = "项目类型" )
    private String projectType;

    /** 专项类别（如：教育专项、医疗专项等） */
    @Excel(name = "专项类别" )
    private String specialType;

    /** 联系方式（手机号） */
    @Excel(name = "联系方式" )
    private String phone;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String email;

    /** 是否有学生干部经历（0=无，1=有） */
    @Excel(name = "是否有学生干部经历" )
    private String hasCadreExp;

    /** 服务省份 */
    @Excel(name = "服务省份")
    private String serviceProvince;

    /** 服务县 */
    @Excel(name = "服务县")
    private String serviceCounty;

    /** 服务单位 */
    @Excel(name = "服务单位")
    private String serviceUnit;

    /** 职位 */
    @Excel(name = "职位")
    private String position;

    /** 岗位编号 */
    @Excel(name = "岗位编号")
    private String postCode;

    /** 服务单位地址 */
    @Excel(name = "服务单位地址")
    private String serviceAddress;

    /** 服务单位联系人 */
    @Excel(name = "服务单位联系人")
    private String contactPerson;

    /** 单位联系电话 */
    @Excel(name = "单位联系电话")
    private String unitPhone;

    @Excel(name = "录取年份")
    private int admitYear;

    private Integer status;


    // 非数据库字段
    private Boolean isFriend; // 是否是我的好友
    private Integer friendStatus; // 0无 1待处理 2同意 3拒绝 4撤回

    // 注意：方法名必须和属性名严格对应
    public Boolean getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(Boolean isFriend) {
        this.isFriend = isFriend;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }




    public int getAdmitYear() {
        return admitYear;
    }

    public void setAdmitYear(int admitYear) {
        this.admitYear = admitYear;
    }



    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setNation(String nation) 
    {
        this.nation = nation;
    }

    public String getNation() 
    {
        return nation;
    }

    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public void setPoliticalStatus(String politicalStatus) 
    {
        this.politicalStatus = politicalStatus;
    }

    public String getPoliticalStatus() 
    {
        return politicalStatus;
    }

    public void setCollege(String college) 
    {
        this.college = college;
    }

    public String getCollege() 
    {
        return college;
    }

    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }

    public void setSchoolCcbCard(String schoolCcbCard) 
    {
        this.schoolCcbCard = schoolCcbCard;
    }

    public String getSchoolCcbCard() 
    {
        return schoolCcbCard;
    }

    public void setNativeProvince(String nativeProvince) 
    {
        this.nativeProvince = nativeProvince;
    }

    public String getNativeProvince() 
    {
        return nativeProvince;
    }

    public void setNativeCity(String nativeCity) 
    {
        this.nativeCity = nativeCity;
    }

    public String getNativeCity() 
    {
        return nativeCity;
    }

    public void setHukouProvince(String hukouProvince) 
    {
        this.hukouProvince = hukouProvince;
    }

    public String getHukouProvince() 
    {
        return hukouProvince;
    }

    public void setHukouCity(String hukouCity) 
    {
        this.hukouCity = hukouCity;
    }

    public String getHukouCity() 
    {
        return hukouCity;
    }

    public void setFamilyAddress(String familyAddress) 
    {
        this.familyAddress = familyAddress;
    }

    public String getFamilyAddress() 
    {
        return familyAddress;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public String getHeight()
    {
        return height;
    }

    public void setPoloSize(String poloSize) 
    {
        this.poloSize = poloSize;
    }

    public String getPoloSize() 
    {
        return poloSize;
    }

    public void setAttendCeremony(String attendCeremony) 
    {
        this.attendCeremony = attendCeremony;
    }

    public String getAttendCeremony() 
    {
        return attendCeremony;
    }

    public void setAssignProvince(String assignProvince) 
    {
        this.assignProvince = assignProvince;
    }

    public String getAssignProvince() 
    {
        return assignProvince;
    }

    public void setHasTeacherCert(String hasTeacherCert) 
    {
        this.hasTeacherCert = hasTeacherCert;
    }

    public String getHasTeacherCert() 
    {
        return hasTeacherCert;
    }

    public void setTeacherSubject(String teacherSubject) 
    {
        this.teacherSubject = teacherSubject;
    }

    public String getTeacherSubject() 
    {
        return teacherSubject;
    }

    public void setProjectType(String projectType) 
    {
        this.projectType = projectType;
    }

    public String getProjectType() 
    {
        return projectType;
    }

    public void setSpecialType(String specialType) 
    {
        this.specialType = specialType;
    }

    public String getSpecialType() 
    {
        return specialType;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setHasCadreExp(String hasCadreExp) 
    {
        this.hasCadreExp = hasCadreExp;
    }

    public String getHasCadreExp() 
    {
        return hasCadreExp;
    }

    public void setServiceProvince(String serviceProvince) 
    {
        this.serviceProvince = serviceProvince;
    }

    public String getServiceProvince() 
    {
        return serviceProvince;
    }

    public void setServiceCounty(String serviceCounty) 
    {
        this.serviceCounty = serviceCounty;
    }

    public String getServiceCounty() 
    {
        return serviceCounty;
    }

    public void setServiceUnit(String serviceUnit) 
    {
        this.serviceUnit = serviceUnit;
    }

    public String getServiceUnit() 
    {
        return serviceUnit;
    }

    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }

    public void setPostCode(String postCode) 
    {
        this.postCode = postCode;
    }

    public String getPostCode() 
    {
        return postCode;
    }

    public void setServiceAddress(String serviceAddress) 
    {
        this.serviceAddress = serviceAddress;
    }

    public String getServiceAddress() 
    {
        return serviceAddress;
    }

    public void setContactPerson(String contactPerson) 
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() 
    {
        return contactPerson;
    }

    public void setUnitPhone(String unitPhone) 
    {
        this.unitPhone = unitPhone;
    }

    public String getUnitPhone() 
    {
        return unitPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("idCard", getIdCard())
            .append("gender", getGender())
            .append("nation", getNation())
            .append("birthDate", getBirthDate())
            .append("politicalStatus", getPoliticalStatus())
            .append("college", getCollege())
            .append("major", getMajor())
            .append("schoolCcbCard", getSchoolCcbCard())
            .append("nativeProvince", getNativeProvince())
            .append("nativeCity", getNativeCity())
            .append("hukouProvince", getHukouProvince())
            .append("hukouCity", getHukouCity())
            .append("familyAddress", getFamilyAddress())
            .append("height", getHeight())
            .append("poloSize", getPoloSize())
            .append("attendCeremony", getAttendCeremony())
            .append("assignProvince", getAssignProvince())
            .append("hasTeacherCert", getHasTeacherCert())
            .append("teacherSubject", getTeacherSubject())
            .append("projectType", getProjectType())
            .append("specialType", getSpecialType())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("hasCadreExp", getHasCadreExp())
            .append("serviceProvince", getServiceProvince())
            .append("serviceCounty", getServiceCounty())
            .append("serviceUnit", getServiceUnit())
            .append("position", getPosition())
            .append("postCode", getPostCode())
            .append("serviceAddress", getServiceAddress())
            .append("contactPerson", getContactPerson())
            .append("unitPhone", getUnitPhone())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
