package com.ruoyi.system.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 论坛板块对象 forum_board
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class ForumBoard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 板块名称 */
    @Excel(name = "板块名称")
    private String boardName;

    /** 板块描述 */
    @Excel(name = "板块描述")
    private String boardDesc;

    /** 帖子数 */
    @Excel(name = "帖子数")
    private Long postCount;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 状态（0禁用1启用） */
    @Excel(name = "状态", readConverterExp = "0=禁用,1=启用")
    private Integer status;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setBoardName(String boardName)
    {
        this.boardName = boardName;
    }

    public String getBoardName()
    {
        return boardName;
    }

    public void setBoardDesc(String boardDesc)
    {
        this.boardDesc = boardDesc;
    }

    public String getBoardDesc()
    {
        return boardDesc;
    }

    public void setPostCount(Long postCount)
    {
        this.postCount = postCount;
    }

    public Long getPostCount()
    {
        return postCount;
    }

    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
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
                .append("boardName", getBoardName())
                .append("boardDesc", getBoardDesc())
                .append("postCount", getPostCount())
                .append("sort", getSort())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
