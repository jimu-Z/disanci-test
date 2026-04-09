package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校友个人风采对象 alumni_style
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class AlumniStyle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 校友用户ID（关联sys_user.id） */
    @Excel(name = "校友用户ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    /** 风采名称 */
    @Excel(name = "风采名称")
    private String styleName;

    /** 类型（1图片2文字） */
    @Excel(name = "类型", readConverterExp = "1=图片2文字")
    private Integer styleType;

    /** 文字内容（富文本） */
    @Excel(name = "文字内容", readConverterExp = "富=文本")
    private String styleContent;

    /** 媒体附件（OSS地址，多个用,分隔） */
    @Excel(name = "媒体附件", readConverterExp = "O=SS地址，多个用,分隔")
    private String styleAttach;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 是否公开（0私密1公开） */
    @Excel(name = "是否公开", readConverterExp = "0=私密1公开")
    private Integer isPublic;

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

    public void setStyleName(String styleName) 
    {
        this.styleName = styleName;
    }

    public String getStyleName() 
    {
        return styleName;
    }

    public void setStyleType(Integer styleType) 
    {
        this.styleType = styleType;
    }

    public Integer getStyleType() 
    {
        return styleType;
    }

    public void setStyleContent(String styleContent) 
    {
        this.styleContent = styleContent;
    }

    public String getStyleContent() 
    {
        return styleContent;
    }

    public void setStyleAttach(String styleAttach) 
    {
        this.styleAttach = styleAttach;
    }

    public String getStyleAttach() 
    {
        return styleAttach;
    }

    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    public void setIsPublic(Integer isPublic) 
    {
        this.isPublic = isPublic;
    }

    public Integer getIsPublic() 
    {
        return isPublic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("styleName", getStyleName())
            .append("styleType", getStyleType())
            .append("styleContent", getStyleContent())
            .append("styleAttach", getStyleAttach())
            .append("sort", getSort())
            .append("isPublic", getIsPublic())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
