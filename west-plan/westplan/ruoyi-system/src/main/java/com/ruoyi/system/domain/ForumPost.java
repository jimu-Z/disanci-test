package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 论坛帖子对象 forum_post
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class ForumPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 板块ID（关联forum_board.id） */
    @Excel(name = "板块ID", readConverterExp = "关=联forum_board.id")
    private Long boardId;

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Excel(name = "板块名称", readConverterExp = "关=联forum_board.Name")
    private String boardName;

    /** 发布人ID（关联sys_user.id） */
    @Excel(name = "发布人ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    private String avatar;
    /** 帖子标题 */
    @Excel(name = "帖子标题")
    private String postTitle;

    /** 帖子内容（富文本） */
    @Excel(name = "帖子内容", readConverterExp = "富=文本")
    private String postContent;

    /** 话题标签（多个用,分隔） */
    @Excel(name = "话题标签", readConverterExp = "多=个用,分隔")
    private String postTags;

    /** 附件（OSS地址，多个用,分隔） */
    @Excel(name = "附件", readConverterExp = "O=SS地址，多个用,分隔")
    private String postAttach;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long viewCount;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 评论数 */
    @Excel(name = "评论数")
    private Long commentCount;

    /** 收藏数 */
    @Excel(name = "收藏数")
    private Long collectCount;

    /** 是否置顶（0否1是） */
    @Excel(name = "是否置顶", readConverterExp = "0=否1是")
    private Integer isTop;

    /** 是否热点（0否1是） */
    @Excel(name = "是否热点", readConverterExp = "0=否1是")
    private Integer isHot;

    /** 审核状态（0待审核1通过2驳回） */
    @Excel(name = "审核状态", readConverterExp = "0=待审核1通过2驳回")
    private Integer auditStatus;

    /** 审核备注 */
    @Excel(name = "审核备注")
    private String auditRemark;

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

    public void setBoardId(Long boardId) 
    {
        this.boardId = boardId;
    }

    public Long getBoardId() 
    {
        return boardId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setPostTitle(String postTitle) 
    {
        this.postTitle = postTitle;
    }

    public String getPostTitle() 
    {
        return postTitle;
    }

    public void setPostContent(String postContent) 
    {
        this.postContent = postContent;
    }

    public String getPostContent() 
    {
        return postContent;
    }

    public void setPostTags(String postTags) 
    {
        this.postTags = postTags;
    }

    public String getPostTags() 
    {
        return postTags;
    }

    public void setPostAttach(String postAttach) 
    {
        this.postAttach = postAttach;
    }

    public String getPostAttach() 
    {
        return postAttach;
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

    public void setCommentCount(Long commentCount) 
    {
        this.commentCount = commentCount;
    }

    public Long getCommentCount() 
    {
        return commentCount;
    }

    public void setCollectCount(Long collectCount) 
    {
        this.collectCount = collectCount;
    }

    public Long getCollectCount() 
    {
        return collectCount;
    }

    public void setIsTop(Integer isTop) 
    {
        this.isTop = isTop;
    }

    public Integer getIsTop() 
    {
        return isTop;
    }

    public void setIsHot(Integer isHot) 
    {
        this.isHot = isHot;
    }

    public Integer getIsHot() 
    {
        return isHot;
    }

    public void setAuditStatus(Integer auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditStatus() 
    {
        return auditStatus;
    }

    public void setAuditRemark(String auditRemark) 
    {
        this.auditRemark = auditRemark;
    }

    public String getAuditRemark() 
    {
        return auditRemark;
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
            .append("boardId", getBoardId())
            .append("userId", getUserId())
            .append("postTitle", getPostTitle())
            .append("postContent", getPostContent())
            .append("postTags", getPostTags())
            .append("postAttach", getPostAttach())
            .append("viewCount", getViewCount())
            .append("likeCount", getLikeCount())
            .append("commentCount", getCommentCount())
            .append("collectCount", getCollectCount())
            .append("isTop", getIsTop())
            .append("isHot", getIsHot())
            .append("auditStatus", getAuditStatus())
            .append("auditRemark", getAuditRemark())
            .append("isPublic", getIsPublic())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
