package com.ruoyi.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.AlumniFeelingComment;
import com.ruoyi.system.domain.AlumniFeelingLike;
import com.ruoyi.system.domain.AlumniFeelingReply;
import com.ruoyi.system.domain.dto.FeelingWithUser;
import com.ruoyi.system.mapper.AlumniFeelingCommentMapper;
import com.ruoyi.system.mapper.AlumniFeelingLikeMapper;
import com.ruoyi.system.mapper.AlumniFeelingReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniFeelingMapper;
import com.ruoyi.system.domain.AlumniFeeling;
import com.ruoyi.system.service.IAlumniFeelingService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

 

/**
 * 校友工作感悟Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-14
 */
@Service
public class AlumniFeelingServiceImpl implements IAlumniFeelingService 
{
    @Autowired
    private AlumniFeelingMapper alumniFeelingMapper;
    @Autowired
    private AlumniFeelingLikeMapper likeMapper;

    @Autowired
    private AlumniFeelingCommentMapper commentMapper;
    @Resource
    private RedisCache redisCache;
    @Autowired
    private AlumniFeelingReplyMapper replyMapper;
    private static final Integer VIEW_LIMIT_SECONDS = 60; // 浏览量限流时间（秒）
    private static final Integer INTERACT_LIKE = 1;    // 点赞
    /**
     * 查询校友工作感悟
     * 
     * @param id 校友工作感悟主键
     * @return 校友工作感悟
     */
    @Override
    public AlumniFeeling selectAlumniFeelingById(Long id)
    {
        // 2. 浏览量自增（Redis限流：1分钟内同一用户仅增1次）
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        String redisKey = String.format("alumni:feeling:view:limit:%d:%d", id, userId);
       AlumniFeeling  feeling=alumniFeelingMapper.selectAlumniFeelingById(id);
        if (!redisCache.hasKey(redisKey)) {
            alumniFeelingMapper.incrementViewCount(id);
            redisCache.setCacheObject(redisKey, 1, VIEW_LIMIT_SECONDS, TimeUnit.SECONDS);
            feeling.setViewCount(feeling.getViewCount() + 1); // 实时更新展示
        }
        return feeling;
    }

    /**
     * 查询校友工作感悟列表
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 校友工作感悟
     */
    @Override
    public List<AlumniFeeling> selectAlumniFeelingList(AlumniFeeling alumniFeeling)
    {
        return alumniFeelingMapper.selectAlumniFeelingList(alumniFeeling);


    }
    @Override
    public List<FeelingWithUser> selectFeelingwithUserList(AlumniFeeling alumniFeeling) {
        return alumniFeelingMapper.selectFeelingWithUserList(alumniFeeling);
    }
    @Override
    public  Map<String, Object> selectAlumniFeelingList(AlumniFeeling alumniFeeling,Long userId)
    {
        // 查询感悟列表
        List<AlumniFeeling> feelingList = alumniFeelingMapper.selectAlumniFeelingList(alumniFeeling);
        // 查询总数
        Integer total = alumniFeelingMapper.selectFeelingCount(alumniFeeling);

        // 补充点赞状态和评论列表
        for (AlumniFeeling feeling : feelingList) {
            // 1. 判断当前用户是否点赞
            if (userId != null) {
                AlumniFeelingLike like = likeMapper.selectLikeByFeelingAndUser(feeling.getId(), userId);
                feeling.setIsLiked(like != null && like.getStatus() == 1);
            } else {
                feeling.setIsLiked(false);
            }

            // 2. 查询评论列表
            List<AlumniFeelingComment> commentList = commentMapper.selectCommentByFeelingId(feeling.getId());
            // 补充评论的回复列表
            for (AlumniFeelingComment comment : commentList) {
                List<AlumniFeelingReply> replyList = replyMapper.selectReplyByCommentId(comment.getId());
                comment.setReplies(replyList);
            }
            feeling.setComments(commentList);
        }

        // 组装返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", feelingList);
        resultMap.put("total", total);
        return resultMap;
    }
    /**
     * 新增校友工作感悟
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 结果
     */
    @Override
    public int insertAlumniFeeling(AlumniFeeling alumniFeeling)
    {
        alumniFeeling.setCreateTime(DateUtils.getNowDate());
        return alumniFeelingMapper.insertAlumniFeeling(alumniFeeling);
    }

    /**
     * 修改校友工作感悟
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 结果
     */
    @Override
    public int updateAlumniFeeling(AlumniFeeling alumniFeeling)
    {
        alumniFeeling.setUpdateTime(DateUtils.getNowDate());
        return alumniFeelingMapper.updateAlumniFeeling(alumniFeeling);
    }

    /**
     * 批量删除校友工作感悟
     * 
     * @param ids 需要删除的校友工作感悟主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingByIds(Long[] ids)
    {
        return alumniFeelingMapper.deleteAlumniFeelingByIds(ids);
    }

    /**
     * 删除校友工作感悟信息
     * 
     * @param id 校友工作感悟主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingById(Long id)
    {
        return alumniFeelingMapper.deleteAlumniFeelingById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int likeFeeling(Long feelingId, Long userId, Boolean isLike) {
        if (feelingId == null || userId == null || isLike == null) {
            return 0;
        }

        // 查询点赞记录
        AlumniFeelingLike like = likeMapper.selectLikeByFeelingAndUser(feelingId, userId);
        if (isLike) {
            // 点赞
            if (like == null) {
                // 新增点赞记录
                AlumniFeelingLike newLike = new AlumniFeelingLike();
                newLike.setFeelingId(feelingId);
                newLike.setUserId(userId);
                newLike.setStatus(1);
                likeMapper.insertAlumniFeelingLike(newLike);
                // 点赞数+1
                alumniFeelingMapper.updateLikeCount(feelingId, 1);
            } else if (like.getStatus() == 0) {
                // 恢复点赞
                likeMapper.updateLikeStatus(feelingId, userId, 1);
                alumniFeelingMapper.updateLikeCount(feelingId, 1);
            }
        } else {
            // 取消点赞
            if (like != null && like.getStatus() == 1) {
                likeMapper.updateLikeStatus(feelingId, userId, 0);
                alumniFeelingMapper.updateLikeCount(feelingId, -1);
            }
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addComment(AlumniFeelingComment comment) {
        // 参数校验
        if (comment.getFeelingId() == null || comment.getUserId() == null || !StringUtils.hasText(comment.getContent())) {
            return 0;
        }

        // 新增评论
        commentMapper.insertAlumniFeelingComment(comment);
        // 评论数+1
       return alumniFeelingMapper.updateCommentCount(comment.getFeelingId(), 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addReply(AlumniFeelingReply reply) {
        // 参数校验
        if (reply.getCommentId() == null || reply.getUserId() == null || !StringUtils.hasText(reply.getContent())) {
            return 0;
        }

        // 新增回复
        replyMapper.insertAlumniFeelingReply(reply);

        return 1;

    }


}
