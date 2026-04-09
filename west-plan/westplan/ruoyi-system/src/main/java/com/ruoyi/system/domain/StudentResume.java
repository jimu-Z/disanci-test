package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生履历对象 student_resume
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class StudentResume extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;


    /** 系统用户ID（关联sys_user.id） */
    @Excel(name = "学号")
    private Long userId;

    /** 服务省 */
    @Excel(name = "服务省")
    private String serviceProvince;

    /** 服务市 */
    @Excel(name = "服务市")
    private String serviceCity;

    /** 服务县区 */
    @Excel(name = "服务县区")
    private String serviceCounty;

    /** 服务单位 */
    @Excel(name = "服务单位")
    private String serviceUnit;

    /** 岗位 */
    @Excel(name = "岗位")
    private String postType;

    /** 起止时间 */

    @Excel(name = "起止时间")
    private String admissionTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }



    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setServiceProvince(String serviceProvince) 
    {
        this.serviceProvince = serviceProvince;
    }

    public String getServiceProvince() 
    {
        return serviceProvince;
    }

    public void setServiceCity(String serviceCity) 
    {
        this.serviceCity = serviceCity;
    }

    public String getServiceCity() 
    {
        return serviceCity;
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

    public void setPostType(String postType) 
    {
        this.postType = postType;
    }

    public String getPostType() 
    {
        return postType;
    }

    public void setAdmissionTime(String admissionTime)
    {
        this.admissionTime = admissionTime;
    }

    public String getAdmissionTime()
    {
        return admissionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())

            .append("userId", getUserId())
            .append("serviceProvince", getServiceProvince())
            .append("serviceCity", getServiceCity())
            .append("serviceCounty", getServiceCounty())
            .append("serviceUnit", getServiceUnit())
            .append("postType", getPostType())
            .append("admissionTime", getAdmissionTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
