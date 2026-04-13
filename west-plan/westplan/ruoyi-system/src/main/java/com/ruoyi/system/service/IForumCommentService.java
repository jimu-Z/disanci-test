package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.ForumComment;
import com.ruoyi.system.domain.dto.ForumCommentAddDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 论坛评论Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IForumCommentService 
{
    /**
     * 查询论坛评论
     * 
     * @param id 论坛评论主键
     * @return 论坛评论
     */
    ForumComment selectForumCommentById(Long id);

    /**
     * 查询论坛评论列表
     * 
     * @param forumComment 论坛评论
     * @return 论坛评论集合
     */
    List<ForumComment> selectForumCommentList(ForumComment forumComment);

    /**
     * 新增论坛评论
     * 
     * @param forumComment 论坛评论
     * @return 结果
     */
    int insertForumComment(ForumComment forumComment);

    /**
     * 修改论坛评论
     * 
     * @param forumComment 论坛评论
     * @return 结果
     */
    int updateForumComment(ForumComment forumComment);

    /**
     * 批量删除论坛评论
     * 
     * @param ids 需要删除的论坛评论主键集合
     * @return 结果
     */
    int deleteForumCommentByIds(Long[] ids);

    /**
     * 删除论坛评论信息
     * 
     * @param id 论坛评论主键
     * @return 结果
     */
    int deleteForumCommentById(Long id);

    List<Map<String, Object>> selectCommentTreeByPostId(Long postId);
    List<ForumComment> getTreeByPostId(Long postId);
    @Transactional(rollbackFor = Exception.class)
    int addForumComment(ForumCommentAddDTO dto);

    @Transactional(rollbackFor = Exception.class)
    void likeComment(Long commentId);
    int    deleteCommentByPostId(Long postid) ;
    int deleteCommentById(Long id);
    int deleteCommentByPostIds(Long[] ids);
    List<ForumComment> selectRecentCommentByPostId(Long postId, Integer limit);
}
