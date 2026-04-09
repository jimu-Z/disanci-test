package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;


import java.util.List;

/**
 * 论坛评论对象 forum_comment
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Data
public class ForumComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 帖子ID（关联forum_post.id） */
    @Excel(name = "帖子ID", readConverterExp = "关=联forum_post.id")
    private Long postId;

    /** 评论人ID（关联sys_user.id） */
    @Excel(name = "评论人ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    /** 父评论ID（0为一级评论） */
    @Excel(name = "父评论ID", readConverterExp = "0=为一级评论")
    private Long parentId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String commentContent;



    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeCount;

    /** 审核状态（0待审核1通过2驳回） */
    @Excel(name = "审核状态", readConverterExp = "0=待审核1通过2驳回")
    private Integer auditStatus;


    // 非数据库字段，用于前端展示
    private String userName;
    private String avatar;
    private String parentUserName;
    // 非数据库字段：用于前端展示

    private List<ForumComment> replies;
}
