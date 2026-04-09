package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 感悟评论对象 alumni_feeling_comment
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public class AlumniFeelingComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 感悟ID */
    @Excel(name = "感悟ID")
    private Long feelingId;

    /** 评论用户ID */
    @Excel(name = "评论用户ID")
    private Long userId;

    /** 评论用户姓名 */
    @Excel(name = "评论用户姓名")
    private String userName;

    /** 评论用户头像 */
    @Excel(name = "评论用户头像")
    private String userAvatar;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    /** 状态：1-正常，0-删除 */
    @Excel(name = "状态：1-正常，0-删除")
    private Integer status;

    private List<AlumniFeelingReply> replies;
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

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setUserAvatar(String userAvatar) 
    {
        this.userAvatar = userAvatar;
    }

    public String getUserAvatar() 
    {
        return userAvatar;
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
            .append("feelingId", getFeelingId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("userAvatar", getUserAvatar())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .toString();
    }


    public List<AlumniFeelingReply> getReplies() {
        return replies;
    }

    public void setReplies(List<AlumniFeelingReply> replies) {
        this.replies = replies;
    }
}
