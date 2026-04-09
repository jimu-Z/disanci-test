package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.AlumniFeeling;
import com.ruoyi.system.domain.AlumniFeelingComment;
import com.ruoyi.system.domain.AlumniFeelingReply;
import com.ruoyi.system.domain.dto.FeelingWithUser;

/**
 * 校友工作感悟Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IAlumniFeelingService 
{
    /**
     * 查询校友工作感悟
     * 
     * @param id 校友工作感悟主键
     * @return 校友工作感悟
     */
    AlumniFeeling selectAlumniFeelingById(Long id);

    /**
     * 查询校友工作感悟列表
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 校友工作感悟集合
     */
    List<AlumniFeeling> selectAlumniFeelingList(AlumniFeeling alumniFeeling);
    Map<String, Object> selectAlumniFeelingList(AlumniFeeling alumniFeeling, Long userId);
    /**
     * 新增校友工作感悟
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 结果
     */
    int insertAlumniFeeling(AlumniFeeling alumniFeeling);

    /**
     * 修改校友工作感悟
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 结果
     */
    int updateAlumniFeeling(AlumniFeeling alumniFeeling);

    /**
     * 批量删除校友工作感悟
     * 
     * @param ids 需要删除的校友工作感悟主键集合
     * @return 结果
     */
    int deleteAlumniFeelingByIds(Long[] ids);

    /**
     * 删除校友工作感悟信息
     * 
     * @param id 校友工作感悟主键
     * @return 结果
     */
    int deleteAlumniFeelingById(Long id);
    /**
     * 点赞/取消点赞
     */
    int likeFeeling(Long feelingId, Long userId, Boolean isLike);

    /**
     * 发布评论
     */
    int addComment(AlumniFeelingComment comment);

    /**
     * 发布回复
     */
    int addReply(AlumniFeelingReply reply);

    List<FeelingWithUser> selectFeelingwithUserList(AlumniFeeling alumniFeeling);
}
