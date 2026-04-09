package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公告未读记录对象 news_notice_unread
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class NewsNoticeUnread extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 公告ID（关联news_notice.id） */
    @Excel(name = "公告ID", readConverterExp = "关=联news_notice.id")
    private Long noticeId;

    /** 用户ID（关联sys_user.id） */
    @Excel(name = "用户ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setNoticeId(Long noticeId) 
    {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() 
    {
        return noticeId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("noticeId", getNoticeId())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
