package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniVideoMapper;
import com.ruoyi.system.domain.AlumniVideo;
import com.ruoyi.system.service.IAlumniVideoService;

/**
 * 校友视频Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class AlumniVideoServiceImpl implements IAlumniVideoService 
{
    @Autowired
    private AlumniVideoMapper alumniVideoMapper;

    /**
     * 查询校友视频
     * 
     * @param id 校友视频主键
     * @return 校友视频
     */
    @Override
    public AlumniVideo selectAlumniVideoById(Long id)
    {
        return alumniVideoMapper.selectAlumniVideoById(id);
    }

    /**
     * 查询校友视频列表
     * 
     * @param alumniVideo 校友视频
     * @return 校友视频
     */
    @Override
    public List<AlumniVideo> selectAlumniVideoList(AlumniVideo alumniVideo)
    {
        return alumniVideoMapper.selectAlumniVideoList(alumniVideo);
    }

    /**
     * 新增校友视频
     * 
     * @param alumniVideo 校友视频
     * @return 结果
     */
    @Override
    public int insertAlumniVideo(AlumniVideo alumniVideo)
    {
        alumniVideo.setCreateTime(DateUtils.getNowDate());
        return alumniVideoMapper.insertAlumniVideo(alumniVideo);
    }

    /**
     * 修改校友视频
     * 
     * @param alumniVideo 校友视频
     * @return 结果
     */
    @Override
    public int updateAlumniVideo(AlumniVideo alumniVideo)
    {
        alumniVideo.setUpdateTime(DateUtils.getNowDate());
        return alumniVideoMapper.updateAlumniVideo(alumniVideo);
    }

    /**
     * 批量删除校友视频
     * 
     * @param ids 需要删除的校友视频主键
     * @return 结果
     */
    @Override
    public int deleteAlumniVideoByIds(Long[] ids)
    {
        return alumniVideoMapper.deleteAlumniVideoByIds(ids);
    }

    /**
     * 删除校友视频信息
     * 
     * @param id 校友视频主键
     * @return 结果
     */
    @Override
    public int deleteAlumniVideoById(Long id)
    {
        return alumniVideoMapper.deleteAlumniVideoById(id);
    }
}
