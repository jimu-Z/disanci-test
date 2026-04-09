package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校友荣誉彰对象 alumni_honor
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class AlumniHonor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 校友用户ID（关联sys_user.id） */
    @Excel(name = "校友用户ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    /** 荣誉名称 */
    @Excel(name = "荣誉名称")
    private String honorName;

    /** 荣誉级别（校级/市级/省级/国家级） */
    @Excel(name = "荣誉级别", readConverterExp = "校=级/市级/省级/国家级")
    private String honorLevel;

    /** 获得时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "获得时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date obtainTime;

    /** 颁发单位 */
    @Excel(name = "颁发单位")
    private String issueUnit;

    /** 荣誉附件（OSS地址，多个用,分隔） */
    @Excel(name = "荣誉附件", readConverterExp = "O=SS地址，多个用,分隔")
    private String honorAttach;

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

    public void setHonorName(String honorName) 
    {
        this.honorName = honorName;
    }

    public String getHonorName() 
    {
        return honorName;
    }

    public void setHonorLevel(String honorLevel) 
    {
        this.honorLevel = honorLevel;
    }

    public String getHonorLevel() 
    {
        return honorLevel;
    }

    public void setObtainTime(Date obtainTime) 
    {
        this.obtainTime = obtainTime;
    }

    public Date getObtainTime() 
    {
        return obtainTime;
    }

    public void setIssueUnit(String issueUnit) 
    {
        this.issueUnit = issueUnit;
    }

    public String getIssueUnit() 
    {
        return issueUnit;
    }

    public void setHonorAttach(String honorAttach) 
    {
        this.honorAttach = honorAttach;
    }

    public String getHonorAttach() 
    {
        return honorAttach;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("honorName", getHonorName())
            .append("honorLevel", getHonorLevel())
            .append("obtainTime", getObtainTime())
            .append("issueUnit", getIssueUnit())
            .append("honorAttach", getHonorAttach())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
