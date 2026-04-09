package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ForumComment;
import org.apache.ibatis.annotations.Param;

/**
 * 论坛评论Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface ForumCommentMapper 
{
    // 基础CRUD（MyBatis Generator生成）

    int insert(ForumComment record);

    ForumComment selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(ForumComment record);
    int updateByPrimaryKey(ForumComment record);

    // 自定义方法
    /** 根据帖子ID查询评论列表（联表用户，平级） */
    List<ForumComment> selectCommentListByPostId(Long postId);
    /** 根据父评论ID查询子评论 */
    List<ForumComment> selectChildCommentByParentId(Long parentId);
    /** 评论点赞数自增 */
    int incrementLikeCount(Long id);
    /** 批量删除评论 */
    int deleteCommentByParentIds(@Param("ids") List<Long> ids);
    /** 根据帖子ID删除所有评论 */
    int deleteCommentByPostIds(Long[] ids);

    int deleteCommentByPostId(Long postid);

    int deleteCommentById(Long id);
}
