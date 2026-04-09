package com.ruoyi.system.domain.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 评论发布请求DTO
 *封装前端请求参数，实现参数隔离，避免直接传递实体类，包含帖子发布、评论发布的专用 DTO。
 * @author 西部计划校友平台
 * @date 2026-02-02
 */
@Data
public class ForumCommentAddDTO {
    /** 帖子ID */
    @NotNull(message = "帖子ID不能为空")
    private Long postId;

    /** 父评论ID（0为一级评论） */
    private Long parentId = 0L;

    /** 评论内容 */
    @NotBlank(message = "评论内容不能为空")
    private String commentContent;
}