package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AlumniVideo;

/**
 * 校友视频Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IAlumniVideoService 
{
    /**
     * 查询校友视频
     * 
     * @param id 校友视频主键
     * @return 校友视频
     */
    AlumniVideo selectAlumniVideoById(Long id);

    /**
     * 查询校友视频列表
     * 
     * @param alumniVideo 校友视频
     * @return 校友视频集合
     */
    List<AlumniVideo> selectAlumniVideoList(AlumniVideo alumniVideo);

    /**
     * 新增校友视频
     * 
     * @param alumniVideo 校友视频
     * @return 结果
     */
    int insertAlumniVideo(AlumniVideo alumniVideo);

    /**
     * 修改校友视频
     * 
     * @param alumniVideo 校友视频
     * @return 结果
     */
    int updateAlumniVideo(AlumniVideo alumniVideo);

    /**
     * 批量删除校友视频
     * 
     * @param ids 需要删除的校友视频主键集合
     * @return 结果
     */
    int deleteAlumniVideoByIds(Long[] ids);

    /**
     * 删除校友视频信息
     * 
     * @param id 校友视频主键
     * @return 结果
     */
    int deleteAlumniVideoById(Long id);
}
