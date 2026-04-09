package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 校友工作感悟对象 alumni_feeling
 * 
 * @author ruoyi
 * @date 2026-02-14
 */
public class AlumniFeeling extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 账号 */
    @Excel(name = "账号")
    private Long userId;

    /** 类别 */
    @Excel(name = "类别")
    private String category;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容（富文本） */
    @Excel(name = "内容", readConverterExp = "富=文本")
    private String content;

    /** 图片 */
    @Excel(name = "图片")
    private String picture;

    /** 视频 */
    @Excel(name = "视频")
    private String vedio;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long viewCount;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 是否公开（是，否） */
    @Excel(name = "是否公开", readConverterExp = "是=，否")
    private String isPublic;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long showOrder;
    /** 点赞状态 */
    private boolean isLiked;
    /** 评论列表 */
    private List<AlumniFeelingComment> comments;
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

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
    }

    public void setVedio(String vedio) 
    {
        this.vedio = vedio;
    }

    public String getVedio() 
    {
        return vedio;
    }

    public void setViewCount(Long viewCount) 
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount() 
    {
        return viewCount;
    }

    public void setLikeCount(Long likeCount) 
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount() 
    {
        return likeCount;
    }

    public void setIsPublic(String isPublic) 
    {
        this.isPublic = isPublic;
    }

    public String getIsPublic() 
    {
        return isPublic;
    }

    public void setShowOrder(Long showOrder) 
    {
        this.showOrder = showOrder;
    }

    public Long getShowOrder() 
    {
        return showOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("category", getCategory())
            .append("title", getTitle())
            .append("content", getContent())
            .append("picture", getPicture())
            .append("vedio", getVedio())
            .append("viewCount", getViewCount())
            .append("likeCount", getLikeCount())
            .append("isPublic", getIsPublic())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("showOrder", getShowOrder())
            .toString();
    }


    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean liked) {
        isLiked = liked;
    }

    public List<AlumniFeelingComment> getComments() {
        return comments;
    }

    public void setComments(List<AlumniFeelingComment> comments) {
        this.comments = comments;
    }
}
