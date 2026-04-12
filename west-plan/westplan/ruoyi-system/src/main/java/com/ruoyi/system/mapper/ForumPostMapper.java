package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ForumPost;
import org.apache.ibatis.annotations.Param;
/**
 * 论坛帖子Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface ForumPostMapper 
{
    // 基础CRUD（MyBatis Generator生成）
    int deleteByPrimaryKey(Long id);
    int insert(ForumPost record);
    int insertSelective(ForumPost record);
    ForumPost selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(ForumPost record);
    int updateByPrimaryKey(ForumPost record);

    // 自定义方法
    /** 分页查询帖子列表（联表：板块+用户） */
    List<ForumPost> selectPostList(ForumPost forumPost);
    /** 查询热门置顶帖子（论坛首页） */
    List<ForumPost> selectTopHotPostList();
    /** 查询最新发布帖子（论坛首页） */
    List<ForumPost> selectNewPostList(@Param("userId")Long userId,@Param("limit") Integer limit);
    List<ForumPost> selectMyPosts(@Param("userId")Long userId,@Param("limit") Integer limit);
    /** 帖子详情（联表） */
    ForumPost selectPostDetailById(@Param("id") Long id, @Param("currentUserId") Long currentUserId);
    /** 浏览量自增（防重复） */
    int incrementViewCount(Long id);
    /** 数值自增（点赞/收藏/评论） */
    int incrementCount(@Param("id") Long id, @Param("column") String column);
    /** 数值自减（点赞/收藏/评论） */
    int decrementCount(@Param("id") Long id, @Param("column") String column);
    /** 批量删除帖子 */
    int deletePostByIds(@Param("ids") Long[] ids);

    void deleteCommentByPostIds(Long[] ids);

    void incrementReplyCount(Long id);

    void decrementReplyCount(Long id);


}
