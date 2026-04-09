package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 题库信息对象 question_bank
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class QuestionBank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 题库名称 */
    @Excel(name = "题库名称")
    private String bankName;

    /** 所属批次 */
    @Excel(name = "所属批次")
    private String batch;

    /** 题目内容 */
    @Excel(name = "题目内容")
    private String questionContent;

    /** 正确答案 */
    @Excel(name = "正确答案")
    private String correctAnswer;

    /** 开放开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开放开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date openStartTime;

    /** 开放结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开放结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date openEndTime;

    /** 状态（0禁用1启用） */
    @Excel(name = "状态", readConverterExp = "0=禁用1启用")
    private Integer status;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }

    public void setBatch(String batch) 
    {
        this.batch = batch;
    }

    public String getBatch() 
    {
        return batch;
    }

    public void setQuestionContent(String questionContent) 
    {
        this.questionContent = questionContent;
    }

    public String getQuestionContent() 
    {
        return questionContent;
    }

    public void setCorrectAnswer(String correctAnswer) 
    {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() 
    {
        return correctAnswer;
    }

    public void setOpenStartTime(Date openStartTime) 
    {
        this.openStartTime = openStartTime;
    }

    public Date getOpenStartTime() 
    {
        return openStartTime;
    }

    public void setOpenEndTime(Date openEndTime) 
    {
        this.openEndTime = openEndTime;
    }

    public Date getOpenEndTime() 
    {
        return openEndTime;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bankName", getBankName())
            .append("batch", getBatch())
            .append("questionContent", getQuestionContent())
            .append("correctAnswer", getCorrectAnswer())
            .append("openStartTime", getOpenStartTime())
            .append("openEndTime", getOpenEndTime())
            .append("status", getStatus())
            .append("sort", getSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
