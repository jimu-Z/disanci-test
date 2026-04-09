package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生服务申请记录（西部计划/三支一扶等）对象 student_apply
 * 
 * @author ruoyi
 * @date 2026-02-10
 */
public class StudentApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表主键，自增序号 */
    private Long id;

    /** 申请序号 */
    @Excel(name = "申请序号")
    private String applyNo;
    /** 学号 */
    @Excel(name = "学号")
    private String userId;
    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String userName;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String politicalStatus;

    /** 学历 */
    @Excel(name = "学历")
    private String education;

    /** 学位 */
    @Excel(name = "学位")
    private String degree;

    /** 学校所在省 */
    @Excel(name = "学校所在省")
    private String schoolProvince;

    /** 学校 */
    @Excel(name = "学校")
    private String schoolName;

    /** 院（系）名称 */
    @Excel(name = "学院")
    private String college;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 专业类别 */
    @Excel(name = "专业类别")
    private String majorType;



    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phone;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String email;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String nativePlace;

    /** 家庭所在地 */
    @Excel(name = "家庭所在地")
    private String familyLocation;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String detailedAddress;

    /** 是否有教师资格证 */
    @Excel(name = "是否有教师资格证")
    private String hasTeacherCert;

    /** 学段学科 */
    @Excel(name = "学段学科")
    private String teacherSubject;

    /** 是否有学生干部经历 */
    @Excel(name = "是否有学生干部经历")
    private String hasCadreExp;

    /** 学生干部经历说明 */
    @Excel(name = "学生干部经历说明")
    private String cadreExpDesc;

    /** 在校期间获奖情况 */
    @Excel(name = "在校期间获奖情况")
    private String awards;

    /** 志愿服务经历 */
    @Excel(name = "志愿服务经历")
    private String volunteerExp;

    /** 其他需说明事项 */
    @Excel(name = "其他需说明事项")
    private String otherDesc;

    /** 服务意向1 */
    @Excel(name = "服务意向1")
    private String intention1;

    /** 服务意向1-项目类型 */
    @Excel(name = "服务意向1-项目类型")
    private String intention1Project;

    /** 服务意向1-服务省 */
    @Excel(name = "服务意向1-服务省")
    private String intention1Province;

    /** 服务意向1-专项类别 */
    @Excel(name = "服务意向1-专项类别")
    private String intention1Special;

    /** 服务意向2 */
    @Excel(name = "服务意向2")
    private String intention2;

    /** 服务意向2-项目类型 */
    @Excel(name = "服务意向2-项目类型")
    private String intention2Project;

    /** 服务意向2-服务省 */
    @Excel(name = "服务意向2-服务省")
    private String intention2Province;

    /** 服务意向2-专项类别 */
    @Excel(name = "服务意向2-专项类别")
    private String intention2Special;

    /** 服务意向3 */
    @Excel(name = "服务意向3")
    private String intention3;

    /** 服务意向3-项目类型 */
    @Excel(name = "服务意向3-项目类型")
    private String intention3Project;

    /** 服务意向3-服务省 */
    @Excel(name = "服务意向3-服务省")
    private String intention3Province;

    /** 服务意向3-专项类别 */
    @Excel(name = "服务意向3-专项类别")
    private String intention3Special;

    /** 意向服务年限 */
    @Excel(name = "意向服务年限")
    private String serviceYears;

    /** 是否服从岗位调剂 */
    @Excel(name = "是否服从岗位调剂")
    private String acceptAdjust;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setApplyNo(String applyNo) 
    {
        this.applyNo = applyNo;
    }

    public String getApplyNo() 
    {
        return applyNo;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }

    public void setBirthDate(Date birthDate) 
    {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() 
    {
        return birthDate;
    }

    public void setNation(String nation) 
    {
        this.nation = nation;
    }

    public String getNation() 
    {
        return nation;
    }

    public void setPoliticalStatus(String politicalStatus) 
    {
        this.politicalStatus = politicalStatus;
    }

    public String getPoliticalStatus() 
    {
        return politicalStatus;
    }

    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }

    public void setDegree(String degree) 
    {
        this.degree = degree;
    }

    public String getDegree() 
    {
        return degree;
    }

    public void setSchoolProvince(String schoolProvince) 
    {
        this.schoolProvince = schoolProvince;
    }

    public String getSchoolProvince() 
    {
        return schoolProvince;
    }

    public void setSchoolName(String schoolName) 
    {
        this.schoolName = schoolName;
    }

    public String getSchoolName() 
    {
        return schoolName;
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

    public void setMajorType(String majorType) 
    {
        this.majorType = majorType;
    }

    public String getMajorType() 
    {
        return majorType;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
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

    public void setNativePlace(String nativePlace) 
    {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace() 
    {
        return nativePlace;
    }

    public void setFamilyLocation(String familyLocation) 
    {
        this.familyLocation = familyLocation;
    }

    public String getFamilyLocation() 
    {
        return familyLocation;
    }

    public void setDetailedAddress(String detailedAddress) 
    {
        this.detailedAddress = detailedAddress;
    }

    public String getDetailedAddress() 
    {
        return detailedAddress;
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

    public void setHasCadreExp(String hasCadreExp) 
    {
        this.hasCadreExp = hasCadreExp;
    }

    public String getHasCadreExp() 
    {
        return hasCadreExp;
    }

    public void setCadreExpDesc(String cadreExpDesc) 
    {
        this.cadreExpDesc = cadreExpDesc;
    }

    public String getCadreExpDesc() 
    {
        return cadreExpDesc;
    }

    public void setAwards(String awards) 
    {
        this.awards = awards;
    }

    public String getAwards() 
    {
        return awards;
    }

    public void setVolunteerExp(String volunteerExp) 
    {
        this.volunteerExp = volunteerExp;
    }

    public String getVolunteerExp() 
    {
        return volunteerExp;
    }

    public void setOtherDesc(String otherDesc) 
    {
        this.otherDesc = otherDesc;
    }

    public String getOtherDesc() 
    {
        return otherDesc;
    }

    public void setIntention1(String intention1) 
    {
        this.intention1 = intention1;
    }

    public String getIntention1() 
    {
        return intention1;
    }

    public void setIntention1Project(String intention1Project) 
    {
        this.intention1Project = intention1Project;
    }

    public String getIntention1Project() 
    {
        return intention1Project;
    }

    public void setIntention1Province(String intention1Province) 
    {
        this.intention1Province = intention1Province;
    }

    public String getIntention1Province() 
    {
        return intention1Province;
    }

    public void setIntention1Special(String intention1Special) 
    {
        this.intention1Special = intention1Special;
    }

    public String getIntention1Special() 
    {
        return intention1Special;
    }

    public void setIntention2(String intention2) 
    {
        this.intention2 = intention2;
    }

    public String getIntention2() 
    {
        return intention2;
    }

    public void setIntention2Project(String intention2Project) 
    {
        this.intention2Project = intention2Project;
    }

    public String getIntention2Project() 
    {
        return intention2Project;
    }

    public void setIntention2Province(String intention2Province) 
    {
        this.intention2Province = intention2Province;
    }

    public String getIntention2Province() 
    {
        return intention2Province;
    }

    public void setIntention2Special(String intention2Special) 
    {
        this.intention2Special = intention2Special;
    }

    public String getIntention2Special() 
    {
        return intention2Special;
    }

    public void setIntention3(String intention3) 
    {
        this.intention3 = intention3;
    }

    public String getIntention3() 
    {
        return intention3;
    }

    public void setIntention3Project(String intention3Project) 
    {
        this.intention3Project = intention3Project;
    }

    public String getIntention3Project() 
    {
        return intention3Project;
    }

    public void setIntention3Province(String intention3Province) 
    {
        this.intention3Province = intention3Province;
    }

    public String getIntention3Province() 
    {
        return intention3Province;
    }

    public void setIntention3Special(String intention3Special) 
    {
        this.intention3Special = intention3Special;
    }

    public String getIntention3Special() 
    {
        return intention3Special;
    }

    public void setServiceYears(String serviceYears) 
    {
        this.serviceYears = serviceYears;
    }

    public String getServiceYears() 
    {
        return serviceYears;
    }

    public void setAcceptAdjust(String acceptAdjust) 
    {
        this.acceptAdjust = acceptAdjust;
    }

    public String getAcceptAdjust() 
    {
        return acceptAdjust;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applyNo", getApplyNo())
            .append("userName", getUserName())
            .append("gender", getGender())
            .append("idCard", getIdCard())
            .append("birthDate", getBirthDate())
            .append("nation", getNation())
            .append("politicalStatus", getPoliticalStatus())
            .append("education", getEducation())
            .append("degree", getDegree())
            .append("schoolProvince", getSchoolProvince())
            .append("schoolName", getSchoolName())
            .append("college", getCollege())
            .append("major", getMajor())
            .append("majorType", getMajorType())
            .append("userId", getUserId())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("nativePlace", getNativePlace())
            .append("familyLocation", getFamilyLocation())
            .append("detailedAddress", getDetailedAddress())
            .append("hasTeacherCert", getHasTeacherCert())
            .append("teacherSubject", getTeacherSubject())
            .append("hasCadreExp", getHasCadreExp())
            .append("cadreExpDesc", getCadreExpDesc())
            .append("awards", getAwards())
            .append("volunteerExp", getVolunteerExp())
            .append("otherDesc", getOtherDesc())
            .append("intention1", getIntention1())
            .append("intention1Project", getIntention1Project())
            .append("intention1Province", getIntention1Province())
            .append("intention1Special", getIntention1Special())
            .append("intention2", getIntention2())
            .append("intention2Project", getIntention2Project())
            .append("intention2Province", getIntention2Province())
            .append("intention2Special", getIntention2Special())
            .append("intention3", getIntention3())
            .append("intention3Project", getIntention3Project())
            .append("intention3Province", getIntention3Province())
            .append("intention3Special", getIntention3Special())
            .append("serviceYears", getServiceYears())
            .append("acceptAdjust", getAcceptAdjust())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
