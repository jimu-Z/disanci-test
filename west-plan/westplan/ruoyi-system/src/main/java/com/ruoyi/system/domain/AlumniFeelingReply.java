package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评论回复对象 alumni_feeling_reply
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public class AlumniFeelingReply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 评论ID */
    @Excel(name = "评论ID")
    private Long commentId;

    /** 回复用户ID */
    @Excel(name = "回复用户ID")
    private Long userId;

    /** 回复用户姓名 */
    @Excel(name = "回复用户姓名")
    private String userName;

    /** 回复内容 */
    @Excel(name = "回复内容")
    private String content;

    /** 状态：1-正常，0-删除 */
    @Excel(name = "状态：1-正常，0-删除")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCommentId(Long commentId) 
    {
        this.commentId = commentId;
    }

    public Long getCommentId() 
    {
        return commentId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
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

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
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
            .append("commentId", getCommentId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .toString();
    }
}
