package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 新闻公告对象 news_notice
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class NewsNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 类型（1公告2新闻） */
    @Excel(name = "类型", readConverterExp = "1=公告2新闻")
    private Integer noticeType;

    /** 标题 */
    @Excel(name = "标题")
    private String noticeTitle;

    /** 内容（富文本） */
    @Excel(name = "内容", readConverterExp = "富=文本")
    private String noticeContent;

    /** 附件（OSS地址，多个用,分隔） */
    @Excel(name = "附件", readConverterExp = "O=SS地址，多个用,分隔")
    private String noticeAttach;

    /** 发布范围（角色编码，多个用,分隔，*为全部） */
    @Excel(name = "发布范围", readConverterExp = "角=色编码，多个用,分隔，*为全部")
    private String publishScope;

    /** 是否置顶（0否1是） */
    @Excel(name = "是否置顶", readConverterExp = "0=否1是")
    private Integer isTop;

    /** 发布人 */
    @Excel(name = "发布人")
    private String publishBy;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 状态（0禁用1启用） */
    @Excel(name = "状态", readConverterExp = "0=禁用1启用")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setNoticeType(Integer noticeType) 
    {
        this.noticeType = noticeType;
    }

    public Integer getNoticeType() 
    {
        return noticeType;
    }

    public void setNoticeTitle(String noticeTitle) 
    {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeTitle() 
    {
        return noticeTitle;
    }

    public void setNoticeContent(String noticeContent) 
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() 
    {
        return noticeContent;
    }

    public void setNoticeAttach(String noticeAttach) 
    {
        this.noticeAttach = noticeAttach;
    }

    public String getNoticeAttach() 
    {
        return noticeAttach;
    }

    public void setPublishScope(String publishScope) 
    {
        this.publishScope = publishScope;
    }

    public String getPublishScope() 
    {
        return publishScope;
    }

    public void setIsTop(Integer isTop) 
    {
        this.isTop = isTop;
    }

    public Integer getIsTop() 
    {
        return isTop;
    }

    public void setPublishBy(String publishBy) 
    {
        this.publishBy = publishBy;
    }

    public String getPublishBy() 
    {
        return publishBy;
    }

    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
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
            .append("noticeType", getNoticeType())
            .append("noticeTitle", getNoticeTitle())
            .append("noticeContent", getNoticeContent())
            .append("noticeAttach", getNoticeAttach())
            .append("publishScope", getPublishScope())
            .append("isTop", getIsTop())
            .append("publishBy", getPublishBy())
            .append("publishTime", getPublishTime())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
