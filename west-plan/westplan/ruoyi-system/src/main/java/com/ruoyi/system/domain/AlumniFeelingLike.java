package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 感悟点赞记录对象 alumni_feeling_like
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public class AlumniFeelingLike extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 感悟ID */
    @Excel(name = "感悟ID")
    private Long feelingId;

    /** 点赞用户ID */
    @Excel(name = "点赞用户ID")
    private Long userId;

    /** 状态：1-点赞，0-取消 */
    @Excel(name = "状态：1-点赞，0-取消")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setFeelingId(Long feelingId) 
    {
        this.feelingId = feelingId;
    }

    public Long getFeelingId() 
    {
        return feelingId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("feelingId", getFeelingId())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .toString();
    }
}
