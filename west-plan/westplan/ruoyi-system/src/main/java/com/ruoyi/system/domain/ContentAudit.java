package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 内容审核记录对象 content_audit
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class ContentAudit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 发布人ID（关联sys_user.id） */
    @Excel(name = "发布人ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    /** 内容类型（1帖子2评论3感悟4风采5视频） */
    @Excel(name = "内容类型", readConverterExp = "1=帖子2评论3感悟4风采5视频")
    private Integer contentType;

    /** 内容ID（对应各表主键） */
    @Excel(name = "内容ID", readConverterExp = "对=应各表主键")
    private Long contentId;

    /** 审核内容（截取前500字） */
    @Excel(name = "审核内容", readConverterExp = "截=取前500字")
    private String contentContent;

    /** 关键词审核（0通过1拦截） */
    @Excel(name = "关键词审核", readConverterExp = "0=通过1拦截")
    private Integer keywordAudit;

    /** AI审核（0通过1拦截） */
    @Excel(name = "AI审核", readConverterExp = "0=通过1拦截")
    private Integer aiAudit;

    /** 最终结果（0通过1拦截） */
    @Excel(name = "最终结果", readConverterExp = "0=通过1拦截")
    private Integer auditResult;

    /** 审核失败原因 */
    @Excel(name = "审核失败原因")
    private String auditMsg;

    /** 报警状态（0未报警1已报警） */
    @Excel(name = "报警状态", readConverterExp = "0=未报警1已报警")
    private Integer alarmStatus;

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

    public void setContentType(Integer contentType) 
    {
        this.contentType = contentType;
    }

    public Integer getContentType() 
    {
        return contentType;
    }

    public void setContentId(Long contentId) 
    {
        this.contentId = contentId;
    }

    public Long getContentId() 
    {
        return contentId;
    }

    public void setContentContent(String contentContent) 
    {
        this.contentContent = contentContent;
    }

    public String getContentContent() 
    {
        return contentContent;
    }

    public void setKeywordAudit(Integer keywordAudit) 
    {
        this.keywordAudit = keywordAudit;
    }

    public Integer getKeywordAudit() 
    {
        return keywordAudit;
    }

    public void setAiAudit(Integer aiAudit) 
    {
        this.aiAudit = aiAudit;
    }

    public Integer getAiAudit() 
    {
        return aiAudit;
    }

    public void setAuditResult(Integer auditResult) 
    {
        this.auditResult = auditResult;
    }

    public Integer getAuditResult() 
    {
        return auditResult;
    }

    public void setAuditMsg(String auditMsg) 
    {
        this.auditMsg = auditMsg;
    }

    public String getAuditMsg() 
    {
        return auditMsg;
    }

    public void setAlarmStatus(Integer alarmStatus) 
    {
        this.alarmStatus = alarmStatus;
    }

    public Integer getAlarmStatus() 
    {
        return alarmStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("contentType", getContentType())
            .append("contentId", getContentId())
            .append("contentContent", getContentContent())
            .append("keywordAudit", getKeywordAudit())
            .append("aiAudit", getAiAudit())
            .append("auditResult", getAuditResult())
            .append("auditMsg", getAuditMsg())
            .append("alarmStatus", getAlarmStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
