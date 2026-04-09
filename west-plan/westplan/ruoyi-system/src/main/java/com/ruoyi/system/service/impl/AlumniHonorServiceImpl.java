package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniHonorMapper;
import com.ruoyi.system.domain.AlumniHonor;
import com.ruoyi.system.service.IAlumniHonorService;

/**
 * 校友荣誉彰Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class AlumniHonorServiceImpl implements IAlumniHonorService 
{
    @Autowired
    private AlumniHonorMapper alumniHonorMapper;

    /**
     * 查询校友荣誉彰
     * 
     * @param id 校友荣誉彰主键
     * @return 校友荣誉彰
     */
    @Override
    public AlumniHonor selectAlumniHonorById(Long id)
    {
        return alumniHonorMapper.selectAlumniHonorById(id);
    }

    /**
     * 查询校友荣誉彰列表
     * 
     * @param alumniHonor 校友荣誉彰
     * @return 校友荣誉彰
     */
    @Override
    public List<AlumniHonor> selectAlumniHonorList(AlumniHonor alumniHonor)
    {
        return alumniHonorMapper.selectAlumniHonorList(alumniHonor);
    }

    /**
     * 新增校友荣誉彰
     * 
     * @param alumniHonor 校友荣誉彰
     * @return 结果
     */
    @Override
    public int insertAlumniHonor(AlumniHonor alumniHonor)
    {
        alumniHonor.setCreateTime(DateUtils.getNowDate());
        return alumniHonorMapper.insertAlumniHonor(alumniHonor);
    }

    /**
     * 修改校友荣誉彰
     * 
     * @param alumniHonor 校友荣誉彰
     * @return 结果
     */
    @Override
    public int updateAlumniHonor(AlumniHonor alumniHonor)
    {
        alumniHonor.setUpdateTime(DateUtils.getNowDate());
        return alumniHonorMapper.updateAlumniHonor(alumniHonor);
    }

    /**
     * 批量删除校友荣誉彰
     * 
     * @param ids 需要删除的校友荣誉彰主键
     * @return 结果
     */
    @Override
    public int deleteAlumniHonorByIds(Long[] ids)
    {
        return alumniHonorMapper.deleteAlumniHonorByIds(ids);
    }

    /**
     * 删除校友荣誉彰信息
     * 
     * @param id 校友荣誉彰主键
     * @return 结果
     */
    @Override
    public int deleteAlumniHonorById(Long id)
    {
        return alumniHonorMapper.deleteAlumniHonorById(id);
    }
}
