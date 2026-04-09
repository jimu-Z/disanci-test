package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AlumniHonor;

/**
 * 校友荣誉彰Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IAlumniHonorService 
{
    /**
     * 查询校友荣誉彰
     * 
     * @param id 校友荣誉彰主键
     * @return 校友荣誉彰
     */
    AlumniHonor selectAlumniHonorById(Long id);

    /**
     * 查询校友荣誉彰列表
     * 
     * @param alumniHonor 校友荣誉彰
     * @return 校友荣誉彰集合
     */
    List<AlumniHonor> selectAlumniHonorList(AlumniHonor alumniHonor);

    /**
     * 新增校友荣誉彰
     * 
     * @param alumniHonor 校友荣誉彰
     * @return 结果
     */
    int insertAlumniHonor(AlumniHonor alumniHonor);

    /**
     * 修改校友荣誉彰
     * 
     * @param alumniHonor 校友荣誉彰
     * @return 结果
     */
    int updateAlumniHonor(AlumniHonor alumniHonor);

    /**
     * 批量删除校友荣誉彰
     * 
     * @param ids 需要删除的校友荣誉彰主键集合
     * @return 结果
     */
    int deleteAlumniHonorByIds(Long[] ids);

    /**
     * 删除校友荣誉彰信息
     * 
     * @param id 校友荣誉彰主键
     * @return 结果
     */
    int deleteAlumniHonorById(Long id);
}
