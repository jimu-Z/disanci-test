package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.dto.ForumCommentAddDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ForumComment;
import com.ruoyi.system.service.IForumCommentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 论坛评论Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/forum_comment")
public class ForumCommentController extends BaseController
{
    @Autowired
    private IForumCommentService forumCommentService;

    /**
     * 根据帖子ID查询评论树形结构（一级+子评论，无需权限）
     */
    @ApiOperation("根据帖子ID查询评论树形")
    @GetMapping("/post/{postId}")
    public AjaxResult selectCommentTreeByPostId(
            @ApiParam(value = "帖子ID", required = true) @PathVariable Long postId
    ) {
        List<Map<String, Object>> commentTree = forumCommentService.selectCommentTreeByPostId(postId);
        return AjaxResult.success(commentTree);
    }

    /**
     * 发布评论/回复评论（登录用户即可）
     */
    @ApiOperation("发布/回复评论")
    @Log(title = "论坛评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(
            @ApiParam(value = "评论参数", required = true) @Validated @RequestBody ForumCommentAddDTO dto
    ) {
        return toAjax(forumCommentService.addForumComment(dto));
    }

    /**
     * 删除评论（发布者/管理员，自动删除子评论）
     */
    @ApiOperation("删除评论")
    @Log(title = "论坛评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(
            @ApiParam(value = "评论ID", required = true) @PathVariable Long id
    ) {
        return toAjax(forumCommentService.deleteCommentById(id));
    }

    /**
     * 评论点赞（登录用户即可，24小时防重复）
     */
    @ApiOperation("评论点赞")
    @PostMapping("/like/{commentId}")
    public AjaxResult likeComment(
            @ApiParam(value = "评论ID", required = true) @PathVariable Long commentId
    ) {
        forumCommentService.likeComment(commentId);
        return AjaxResult.success("点赞成功");
    }
}
