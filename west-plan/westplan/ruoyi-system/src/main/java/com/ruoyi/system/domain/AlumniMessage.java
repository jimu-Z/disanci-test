package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校友私信对象 alumni_message
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class AlumniMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 发送人ID（关联sys_user.id） */
    @Excel(name = "发送人ID", readConverterExp = "关=联sys_user.id")
    private Long sendUserId;

    /** 接收人ID（关联sys_user.id） */
    @Excel(name = "接收人ID", readConverterExp = "关=联sys_user.id")
    private Long receiveUserId;

    /** 私信内容 */
    @Excel(name = "私信内容")
    private String msgContent;

    /** 是否已读（0未读1已读） */
    @Excel(name = "是否已读", readConverterExp = "0=未读1已读")
    private Integer isRead;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /** 已读时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "已读时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date readTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSendUserId(Long sendUserId) 
    {
        this.sendUserId = sendUserId;
    }

    public Long getSendUserId() 
    {
        return sendUserId;
    }

    public void setReceiveUserId(Long receiveUserId) 
    {
        this.receiveUserId = receiveUserId;
    }

    public Long getReceiveUserId() 
    {
        return receiveUserId;
    }

    public void setMsgContent(String msgContent) 
    {
        this.msgContent = msgContent;
    }

    public String getMsgContent() 
    {
        return msgContent;
    }

    public void setIsRead(Integer isRead) 
    {
        this.isRead = isRead;
    }

    public Integer getIsRead() 
    {
        return isRead;
    }

    public void setSendTime(Date sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime() 
    {
        return sendTime;
    }

    public void setReadTime(Date readTime) 
    {
        this.readTime = readTime;
    }

    public Date getReadTime() 
    {
        return readTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sendUserId", getSendUserId())
            .append("receiveUserId", getReceiveUserId())
            .append("msgContent", getMsgContent())
            .append("isRead", getIsRead())
            .append("sendTime", getSendTime())
            .append("readTime", getReadTime())
            .toString();
    }
}
