package com.ruoyi.system.domain.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 帖子发布请求DTO
 *封装前端请求参数，实现参数隔离，避免直接传递实体类，包含帖子发布、评论发布的专用 DTO。
 * @author 西部计划校友平台
 * @date 2026-02-02
 */
@Data
public class ForumPostAddDTO {
    /** 帖子ID */
    private Long id;
    /** 板块ID */
    @NotNull(message = "请选择发布板块")
    private Long boardId;

    /** 帖子标题 */
    @NotBlank(message = "帖子标题不能为空")
    private String postTitle;

    /** 帖子内容（富文本） */
    @NotBlank(message = "帖子内容不能为空")
    private String postContent;

    /** 话题标签（多个） */
    private List<String> postTags;

    /** 附件ID列表（关联文件上传表sys_file的id） */
    private List<Long> attachIds;
    /** 附件（OSS地址，多个用,分隔） */

    private String postAttach;
    /** 是否公开（0私密1公开） */
    @NotNull(message = "请选择发布范围")
    private Integer isPublic;
}