package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 通用点赞/收藏对象 common_interact
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class CommonInteract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 操作人ID（关联sys_user.id） */
    @Excel(name = "操作人ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    /** 目标类型（1帖子2感悟3风采） */
    @Excel(name = "目标类型", readConverterExp = "1=帖子2感悟3风采")
    private Integer targetType;

    /** 目标ID（对应各表主键） */
    @Excel(name = "目标ID", readConverterExp = "对=应各表主键")
    private Long targetId;

    /** 操作类型（1点赞2收藏） */
    @Excel(name = "操作类型", readConverterExp = "1=点赞2收藏")
    private Integer interactType;

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

    public void setTargetType(Integer targetType) 
    {
        this.targetType = targetType;
    }

    public Integer getTargetType() 
    {
        return targetType;
    }

    public void setTargetId(Long targetId) 
    {
        this.targetId = targetId;
    }

    public Long getTargetId() 
    {
        return targetId;
    }

    public void setInteractType(Integer interactType) 
    {
        this.interactType = interactType;
    }

    public Integer getInteractType() 
    {
        return interactType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("targetType", getTargetType())
            .append("targetId", getTargetId())
            .append("interactType", getInteractType())
            .append("createTime", getCreateTime())
            .toString();
    }
}
