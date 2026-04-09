package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AlumniFeelingLike;

/**
 * 感悟点赞记录Service接口
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public interface IAlumniFeelingLikeService 
{
    /**
     * 查询感悟点赞记录
     * 
     * @param id 感悟点赞记录主键
     * @return 感悟点赞记录
     */
    AlumniFeelingLike selectAlumniFeelingLikeById(Long id);

    /**
     * 查询感悟点赞记录列表
     * 
     * @param alumniFeelingLike 感悟点赞记录
     * @return 感悟点赞记录集合
     */
    List<AlumniFeelingLike> selectAlumniFeelingLikeList(AlumniFeelingLike alumniFeelingLike);

    /**
     * 新增感悟点赞记录
     * 
     * @param alumniFeelingLike 感悟点赞记录
     * @return 结果
     */
    int insertAlumniFeelingLike(AlumniFeelingLike alumniFeelingLike);

    /**
     * 修改感悟点赞记录
     * 
     * @param alumniFeelingLike 感悟点赞记录
     * @return 结果
     */
    int updateAlumniFeelingLike(AlumniFeelingLike alumniFeelingLike);

    /**
     * 批量删除感悟点赞记录
     * 
     * @param ids 需要删除的感悟点赞记录主键集合
     * @return 结果
     */
    int deleteAlumniFeelingLikeByIds(Long[] ids);

    /**
     * 删除感悟点赞记录信息
     * 
     * @param id 感悟点赞记录主键
     * @return 结果
     */
    int deleteAlumniFeelingLikeById(Long id);
}
