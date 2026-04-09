package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.ForumPost;
import com.ruoyi.system.domain.dto.ForumCommentAddDTO;
import com.ruoyi.system.mapper.ForumPostMapper;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ForumCommentMapper;
import com.ruoyi.system.domain.ForumComment;
import com.ruoyi.system.service.IForumCommentService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 论坛评论Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class ForumCommentServiceImpl implements IForumCommentService 
{
    @Resource
    private ForumCommentMapper forumCommentMapper;
    @Resource
    private ForumPostMapper forumPostMapper;
    @Resource
    private RedisCache redisCache;

    // 评论点赞限流Redis键前缀
    private static final String COMMENT_LIKE_KEY = "forum:comment:like:limit:%d:%d";
    private static final Integer LIKE_LIMIT_SECONDS = 86400; // 点赞限流时间：24小时

    // 获取评论列表并组装树形结构 (优化版：支持无限层级)与selectCommentTreeByPostId功能相同，返回类型不同
    public List<ForumComment> getTreeByPostId(Long postId) {
        // 1. 查询该帖子下所有评论
        List<ForumComment> allComments = forumCommentMapper.selectCommentListByPostId(postId);

        // 2. 创建Map存储所有评论，方便快速查找父节点 (Key: 评论ID, Value: 评论对象)
        Map<Long, ForumComment> commentMap = new HashMap<>();
        for (ForumComment comment : allComments) {
            comment.setReplies(new ArrayList<>()); // 初始化子列表
            commentMap.put(comment.getId(), comment);
        }

        // 3. 组装树形结构
        List<ForumComment> rootList = new ArrayList<>();
        for (ForumComment comment : allComments) {
            if (comment.getParentId() == 0) {
                // 如果是根评论 (parentId=0)，直接加入结果集
                rootList.add(comment);
            } else {
                // 如果是子评论，找到其父评论，加入父评论的 replies 中
                ForumComment parent = commentMap.get(comment.getParentId());
                if (parent != null) {
                    parent.getReplies().add(comment);
                }
            }
        }
        return rootList;
    }

    @Override
    public List<Map<String, Object>> selectCommentTreeByPostId(Long postId) {
        // 1. 查询平级评论列表
        List<ForumComment> commentList = forumCommentMapper.selectCommentListByPostId(postId);
        if (commentList.isEmpty()) {
            return new ArrayList<>();
        }
        // 2. 按父ID分组
        Map<Long, List<ForumComment>> parentMap = commentList.stream()
                .collect(Collectors.groupingBy(ForumComment::getParentId));
        // 3. 递归构建树形结构
        List<Map<String, Object>> treeList = new ArrayList<>();
        for (ForumComment comment : parentMap.getOrDefault(0L, new ArrayList<>())) {
            Map<String, Object> node = convertToMap(comment);
            node.put("children", buildCommentTree(comment.getId(), parentMap));
            treeList.add(node);
        }
        return treeList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addForumComment(ForumCommentAddDTO dto) {
        // 1. 校验帖子存在且已审核通过
        ForumPost post = forumPostMapper.selectByPrimaryKey(dto.getPostId());
        if (post == null || post.getAuditStatus() != 1) {
            throw new ServiceException("帖子不存在或未审核通过，无法评论");
        }
        // 2. 校验父评论存在（回复时）
        if (dto.getParentId() != 0 && forumCommentMapper.selectByPrimaryKey(dto.getParentId()) == null) {
            throw new ServiceException("回复的评论不存在");
        }
        // 3. 获取当前用户
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 4. 转换DTO为实体
        ForumComment comment = new ForumComment();
        comment.setPostId(dto.getPostId());
        comment.setUserId(user.getUserId());
        comment.setParentId(dto.getParentId());
        comment.setCommentContent(dto.getCommentContent());
        comment.setLikeCount(0L);
        comment.setAuditStatus(1); // 自动审核通过
        comment.setCreateBy(user.getUserName());
        comment.setUpdateBy(user.getUserName());
        // 5. 插入评论+帖子评论数自增
        int result = forumCommentMapper.insert(comment);
        if (result > 0) {
            forumPostMapper.incrementCount(dto.getPostId(), "comment_count");
        }
        return result;
    }

    @Override
    public ForumComment selectForumCommentById(Long id) {
        return null;
    }

    @Override
    public List<ForumComment> selectForumCommentList(ForumComment forumComment) {
        return null;
    }

    @Override
    public int insertForumComment(ForumComment forumComment) {
        return 0;
    }

    @Override
    public int updateForumComment(ForumComment forumComment) {
        return 0;
    }

    @Override
    public int deleteForumCommentByIds(Long[] ids) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteForumCommentById(Long id) {
        // 1. 校验评论存在
        ForumComment comment = forumCommentMapper.selectByPrimaryKey(id);
        if (comment == null) {
            throw new ServiceException("评论不存在");
        }
        // 2. 权限校验
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (!SecurityUtils.hasRole("super_admin") && !SecurityUtils.hasRole("common_admin")
                && !comment.getUserId().equals(user.getUserId())) {
            throw new ServiceException("仅能删除自己发布的评论");
        }
        // 3. 查询所有子评论（递归）
        List<Long> allCommentIds = new ArrayList<>();
        allCommentIds.add(id);
        collectChildCommentIds(id, allCommentIds);
        // 4. 删除评论+帖子评论数自减
        int deleteCount = forumCommentMapper.deleteCommentByParentIds(allCommentIds);
        forumPostMapper.decrementCount(comment.getPostId(), "comment_count");
        return deleteCount;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likeComment(Long commentId) {
        // 1. 校验评论存在
        if (forumCommentMapper.selectByPrimaryKey(commentId) == null) {
            throw new ServiceException("评论不存在");
        }
        // 2. 获取当前用户
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        // 3. Redis防重复点赞（24小时内仅能点赞1次）
        String redisKey = String.format(COMMENT_LIKE_KEY, commentId, userId);
        if (redisCache.hasKey(redisKey)) {
            throw new ServiceException("24小时内已点赞，请勿重复操作");
        }
        // 4. 点赞数自增+Redis记录
        forumCommentMapper.incrementLikeCount(commentId);
        redisCache.setCacheObject(redisKey, 1, LIKE_LIMIT_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public int deleteCommentById(Long id) {
        return forumCommentMapper.deleteCommentById( id);
    }

    @Override
    public int deleteCommentByPostIds(Long[] ids) {
        return forumCommentMapper.deleteCommentByPostIds( ids);
    }

    @Override
    public int    deleteCommentByPostId(Long postid) {
        return forumCommentMapper.deleteCommentByPostId( postid);
    }
    /**
     * 递归收集子评论ID
     */
    private void collectChildCommentIds(Long parentId, List<Long> commentIds) {
        List<ForumComment> childComments = forumCommentMapper.selectChildCommentByParentId(parentId);
        for (ForumComment child : childComments) {
            commentIds.add(child.getId());
            collectChildCommentIds(child.getId(), commentIds);
        }
    }

    /**
     * 递归构建评论树形
     */
    private List<Map<String, Object>> buildCommentTree(Long parentId, Map<Long, List<ForumComment>> parentMap) {
        List<Map<String, Object>> childList = new ArrayList<>();
        List<ForumComment> comments = parentMap.getOrDefault(parentId, new ArrayList<>());
        for (ForumComment comment : comments) {
            Map<String, Object> node = convertToMap(comment);
            node.put("children", buildCommentTree(comment.getId(), parentMap));
            childList.add(node);
        }
        return childList;
    }

    /**
     * 转换为前端树形展示的Map
     */
    private Map<String, Object> convertToMap(ForumComment comment) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", comment.getId());
        map.put("postId", comment.getPostId());
        map.put("parentId", comment.getParentId());
        map.put("commentContent", comment.getCommentContent());
        map.put("likeCount", comment.getLikeCount());
        map.put("createTime", comment.getCreateTime());
        map.put("userName", comment.getUserName());
        map.put("avatar", comment.getAvatar());
        map.put("parentUserName", comment.getParentUserName());
        map.put("userId", comment.getUserId()); // 用于权限判断
        return map;
    }
}
