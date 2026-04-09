package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AlumniFeelingLike;
import org.apache.ibatis.annotations.Param;

/**
 * 感悟点赞记录Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public interface AlumniFeelingLikeMapper 
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
     * 删除感悟点赞记录
     * 
     * @param id 感悟点赞记录主键
     * @return 结果
     */
    int deleteAlumniFeelingLikeById(Long id);

    /**
     * 批量删除感悟点赞记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAlumniFeelingLikeByIds(Long[] ids);
    /**
     * 更新点赞状态
     */
    int updateLikeStatus(@Param("feelingId") Long feelingId, @Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 查询用户是否点赞
     */
    AlumniFeelingLike selectLikeByFeelingAndUser(@Param("feelingId") Long feelingId, @Param("userId") Long userId);
}
