package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniFeelingLikeMapper;
import com.ruoyi.system.domain.AlumniFeelingLike;
import com.ruoyi.system.service.IAlumniFeelingLikeService;

/**
 * 感悟点赞记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
@Service
public class AlumniFeelingLikeServiceImpl implements IAlumniFeelingLikeService 
{
    @Autowired
    private AlumniFeelingLikeMapper alumniFeelingLikeMapper;

    /**
     * 查询感悟点赞记录
     * 
     * @param id 感悟点赞记录主键
     * @return 感悟点赞记录
     */
    @Override
    public AlumniFeelingLike selectAlumniFeelingLikeById(Long id)
    {
        return alumniFeelingLikeMapper.selectAlumniFeelingLikeById(id);
    }

    /**
     * 查询感悟点赞记录列表
     * 
     * @param alumniFeelingLike 感悟点赞记录
     * @return 感悟点赞记录
     */
    @Override
    public List<AlumniFeelingLike> selectAlumniFeelingLikeList(AlumniFeelingLike alumniFeelingLike)
    {
        return alumniFeelingLikeMapper.selectAlumniFeelingLikeList(alumniFeelingLike);
    }

    /**
     * 新增感悟点赞记录
     * 
     * @param alumniFeelingLike 感悟点赞记录
     * @return 结果
     */
    @Override
    public int insertAlumniFeelingLike(AlumniFeelingLike alumniFeelingLike)
    {
        alumniFeelingLike.setCreateTime(DateUtils.getNowDate());
        return alumniFeelingLikeMapper.insertAlumniFeelingLike(alumniFeelingLike);
    }

    /**
     * 修改感悟点赞记录
     * 
     * @param alumniFeelingLike 感悟点赞记录
     * @return 结果
     */
    @Override
    public int updateAlumniFeelingLike(AlumniFeelingLike alumniFeelingLike)
    {
        return alumniFeelingLikeMapper.updateAlumniFeelingLike(alumniFeelingLike);
    }

    /**
     * 批量删除感悟点赞记录
     * 
     * @param ids 需要删除的感悟点赞记录主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingLikeByIds(Long[] ids)
    {
        return alumniFeelingLikeMapper.deleteAlumniFeelingLikeByIds(ids);
    }

    /**
     * 删除感悟点赞记录信息
     * 
     * @param id 感悟点赞记录主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingLikeById(Long id)
    {
        return alumniFeelingLikeMapper.deleteAlumniFeelingLikeById(id);
    }
}
