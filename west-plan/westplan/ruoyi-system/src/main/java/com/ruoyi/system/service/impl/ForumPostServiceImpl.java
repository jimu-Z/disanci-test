package com.ruoyi.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;

import com.ruoyi.system.domain.CommonInteract;
import com.ruoyi.system.domain.dto.ForumPostAddDTO;
import com.ruoyi.system.mapper.CommonInteractMapper;
import com.ruoyi.system.mapper.ForumCommentMapper;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ForumPostMapper;
import com.ruoyi.system.domain.ForumPost;
import com.ruoyi.system.service.IForumPostService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 论坛帖子Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class ForumPostServiceImpl implements IForumPostService 
{
    @Resource
    private ForumPostMapper forumPostMapper;
    @Resource
    private ForumCommentMapper forumCommentMapper;

    @Resource
    private CommonInteractMapper commonInteractMapper;
    @Resource
    private RedisCache redisCache;

    // 常量定义
    private static final Integer VIEW_LIMIT_SECONDS = 60; // 浏览量限流时间（秒）
    private static final Integer INTERACT_LIKE = 1;    // 点赞
    private static final Integer INTERACT_COLLECT = 2; // 收藏
    private static final Integer TARGET_TYPE_POST = 1; // 目标类型-帖子
    private static final Set<String> ALLOWED_COUNTER_COLUMNS = new HashSet<String>() {{
        add("like_count");
        add("collect_count");
        add("comment_count");
    }};


    @Override
    public List<ForumPost> selectPostPage( ForumPost forumPost) {
        // MyBatis原生分页：PageHelper开启分页

        List<ForumPost> list = forumPostMapper.selectPostList(forumPost);
        return  list;
    }

    @Override
    public List<ForumPost> selectTopHotPostList() {
        return forumPostMapper.selectTopHotPostList();
    }

    @Override
    public List<ForumPost> selectNewPostList(Long userId,Integer limit) {
        return forumPostMapper.selectNewPostList(userId,limit == null ? 10 : limit);
    }

    @Override
    public ForumPost selectPostDetail(Long id) {
        // 1. 获取当前用户ID
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        // 2. 校验帖子存在
        ForumPost post = forumPostMapper.selectPostDetailById(id, userId);
        if (post == null) {
            throw new ServiceException("帖子不存在");
        }
        // 3. 浏览量自增（Redis限流：1分钟内同一用户仅增1次）
        String redisKey = String.format("forum:post:view:limit:%d:%d", id, userId);
        if (!redisCache.hasKey(redisKey)) {
            forumPostMapper.incrementViewCount(id);
            redisCache.setCacheObject(redisKey, 1, VIEW_LIMIT_SECONDS, TimeUnit.SECONDS);
            post.setViewCount(post.getViewCount() + 1); // 实时更新展示
        }
        return post;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addForumPost(ForumPostAddDTO dto) {
        // 1. 获取当前登录用户
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 2. 转换DTO为实体
        ForumPost post = new ForumPost();
        post.setBoardId(dto.getBoardId());
        post.setUserId(user.getUserId());
        post.setPostTitle(dto.getPostTitle());
        post.setPostContent(dto.getPostContent());
        post.setPostTags(dto.getPostTags() != null ? String.join(",", dto.getPostTags()) : "");
        post.setPostAttach(dto.getPostAttach());
        post.setIsPublic(dto.getIsPublic());
        // 初始化统计字段
        post.setViewCount(0L);
        post.setLikeCount(0L);
        post.setCommentCount(0L);
        post.setCollectCount(0L);
        post.setIsTop(0);
        post.setIsHot(0);
        post.setAuditStatus(0); // 待审核
        // 填充创建人信息
        post.setCreateBy(user.getUserName());
        post.setUpdateBy(user.getUserName());
        // 3. 插入帖子
        return forumPostMapper.insertSelective(post);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateForumPost(ForumPostAddDTO dto, Long postId) {
        // 1. 校验帖子存在
        ForumPost oldPost = forumPostMapper.selectByPrimaryKey(postId);
        if (oldPost == null) {
            throw new ServiceException("帖子不存在");
        }
        // 2. 校验操作权限（仅自己可修改）
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (!oldPost.getUserId().equals(user.getUserId())) {
            throw new ServiceException("仅能修改自己发布的帖子");
        }
        // 3. 待审核/驳回状态才能修改
        if (oldPost.getAuditStatus() == 1) {
            throw new ServiceException("帖子已审核通过，无法修改");
        }
        // 4. 更新字段
        oldPost.setBoardId(dto.getBoardId());
        oldPost.setPostTitle(dto.getPostTitle());
        oldPost.setPostContent(dto.getPostContent());
        oldPost.setPostTags(dto.getPostTags() != null ? String.join(",", dto.getPostTags()) : "");
        oldPost.setPostAttach(dto.getPostAttach());
        oldPost.setIsPublic(dto.getIsPublic());
        oldPost.setAuditStatus(0); // 重新待审核
        oldPost.setUpdateBy(user.getUserName());
        // 5. 保存修改
        return forumPostMapper.updateByPrimaryKeySelective(oldPost);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int auditPost(Long id, Integer auditStatus, String auditRemark) {
        ForumPost post = forumPostMapper.selectByPrimaryKey(id);

        if (post == null) {
            throw new ServiceException("帖子不存在");
        }
        post.setAuditStatus(auditStatus);
        post.setAuditRemark(auditRemark);
        post.setUpdateBy(SecurityUtils.getLoginUser().getUsername());

        return forumPostMapper.updateByPrimaryKeySelective(post);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int topPost(Long id, Integer isTop) {
        ForumPost post = forumPostMapper.selectByPrimaryKey(id);
        if (post == null) {
            throw new ServiceException("帖子不存在");
        }
        post.setIsTop(isTop);
        post.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return forumPostMapper.updateByPrimaryKeySelective(post);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int hotPost(Long id, Integer isHot) {
        ForumPost post = forumPostMapper.selectByPrimaryKey(id);
        if (post == null) {
            throw new ServiceException("帖子不存在");
        }
        post.setIsHot(isHot);
        post.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return forumPostMapper.updateByPrimaryKeySelective(post);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePostByIds(Long[] ids) {
        // 1. 权限校验：管理员可删所有，普通用户仅删自己的
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (!SecurityUtils.hasRole("super_admin") && !SecurityUtils.hasRole("common_admin")) {
            for (Long id : ids) {
                ForumPost post = forumPostMapper.selectByPrimaryKey(id);
                if (post == null || !post.getUserId().equals(user.getUserId())) {
                    throw new ServiceException("仅能删除自己发布的帖子");
                }
            }
        }
        // 2. 删除帖子+关联评论+关联互动记录
        forumCommentMapper.deleteCommentByPostIds(ids); // 删评论
        commonInteractMapper.deleteInteractByTargetIds(TARGET_TYPE_POST, ids); // 删互动记录
        return forumPostMapper.deletePostByIds(ids); // 删帖子
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likePost(Long postId) {
         handleInteract(postId, INTERACT_LIKE, "like_count");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collectPost(Long postId) {
        handleInteract(postId, INTERACT_COLLECT, "collect_count");
    }

    //增加浏览量
    public void incrementViewCount(Long postId) {
        // 直接调用 XML 中的方法
        forumPostMapper.incrementViewCount(postId);
    }

    //增加回复数
    public void incrementReplyCount(Long postId) {

        forumPostMapper.incrementReplyCount(postId);
    }

    //减少回复数
    public void decrementReplyCount(Long postId) {
        forumPostMapper.decrementReplyCount(postId);
    }
    /**
     * 查询最新发布帖子
     */
    @Override
    public       List<ForumPost> getMyPosts(Long userId, int i) {
        return forumPostMapper.selectMyPosts(userId,i);
    }

    /**
     * 通用互动处理（点赞/收藏）
     * @param postId 帖子ID
     * @param interactType 互动类型：1点赞 2收藏
     * @param column 数据库字段名
     */
    private void handleInteract(Long postId, Integer interactType, String column) {
        // 1. 校验帖子存在
        if (forumPostMapper.selectByPrimaryKey(postId) == null) {
            throw new ServiceException("帖子不存在");
        }
        // 2. 获取当前用户
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        // 3. 查询现有互动记录
        CommonInteract interact = commonInteractMapper.selectInteractByUser(
                userId, TARGET_TYPE_POST, postId, interactType
        );
        if (interact == null) {
            // 未互动：添加记录+数值自增
            CommonInteract newInteract = new CommonInteract();
            newInteract.setUserId(userId);
            newInteract.setTargetType(TARGET_TYPE_POST);
            newInteract.setTargetId(postId);
            newInteract.setInteractType(interactType);
            newInteract.setCreateBy(SecurityUtils.getLoginUser().getUsername());
            commonInteractMapper.insertCommonInteract(newInteract);
            forumPostMapper.incrementCount(postId, column);
        } else {
        if (!ALLOWED_COUNTER_COLUMNS.contains(column)) {
            throw new ServiceException("非法计数字段");
        }
            // 已互动：删除记录+数值自减
            commonInteractMapper.deleteInteractByUser(
                    userId, TARGET_TYPE_POST, postId, interactType
            );
            forumPostMapper.decrementCount(postId, column);
        }
    }



}
