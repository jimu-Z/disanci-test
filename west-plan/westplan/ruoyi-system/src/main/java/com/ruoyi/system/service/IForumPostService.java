package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ruoyi.system.domain.dto.ForumAudit;
import com.ruoyi.system.domain.ForumPost;
import com.ruoyi.system.domain.dto.ForumPostAddDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 论坛帖子Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IForumPostService 
{




    List<ForumPost> selectPostPage(  ForumPost forumPost);

    /**
     * 查询热门置顶帖子
     */
    List<ForumPost> selectTopHotPostList();

    /**
     * 查询最新发布帖子
     */
    List<ForumPost> selectNewPostList(Long userId,Integer limit);

    /**
     * 发布帖子
     */
    int addForumPost(ForumPostAddDTO dto);




    /**
     * 帖子详情（浏览量自增）
     */
    ForumPost selectPostDetail(Long id);
    /**
     * 修改帖子
     */
    @Transactional(rollbackFor = Exception.class)
    int updateForumPost(ForumPostAddDTO dto, Long postId);

    /**
     * 审核帖子
     */
    int auditPost(Long id, Integer auditStatus, String auditRemark);

    /**
     * 批量审核帖子
     */
    int batchAuditPost(ForumAudit forumAudit);

    /**
     * 置顶/取消置顶帖子
     */
    int topPost(Long id, Integer isTop);

    /**
     * 设为热点/取消热点帖子
     */
    int hotPost(Long id, Integer isHot);

    /**
     * 批量删除帖子
     */
    int deletePostByIds(Long[] ids);

    /**
     * 帖子点赞/取消点赞
     */
    void likePost(Long postId);

    /**
     * 帖子收藏/取消收藏
     */
    void collectPost(Long postId);
    //增加浏览量
    public void incrementViewCount(Long postId) ;

    //增加回复数
    public void incrementReplyCount(Long postId) ;

    //减少回复数
    public void decrementReplyCount(Long postId) ;

    /**
     * 查询我的发布帖子
     */
    List<ForumPost> getMyPosts(Long userId, int i);

    /**
     * 推荐帖子（按热度得分）
     */
    List<ForumPost> selectRecommendPostList(Integer limit, Long boardId, String keyword);

    /**
     * 论坛统计概览
     */
    Map<String, Object> getForumStats();

    /**
     * 获取论坛热更新统一秒级时间戳
     */
    Long getHotUpdateTs();
}
