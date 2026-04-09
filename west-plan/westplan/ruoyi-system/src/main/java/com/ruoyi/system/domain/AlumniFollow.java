package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校友关注对象 alumni_follow
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class AlumniFollow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关注人ID（关联sys_user.id） */
    @Excel(name = "关注人ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    /** 被关注人ID（关联sys_user.id） */
    @Excel(name = "被关注人ID", readConverterExp = "关=联sys_user.id")
    private Long followUserId;

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

    public void setFollowUserId(Long followUserId) 
    {
        this.followUserId = followUserId;
    }

    public Long getFollowUserId() 
    {
        return followUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("followUserId", getFollowUserId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
