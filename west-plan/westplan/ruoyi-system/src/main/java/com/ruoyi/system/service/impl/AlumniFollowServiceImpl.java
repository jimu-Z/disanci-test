package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniFollowMapper;
import com.ruoyi.system.domain.AlumniFollow;
import com.ruoyi.system.service.IAlumniFollowService;

/**
 * 校友关注Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class AlumniFollowServiceImpl implements IAlumniFollowService 
{
    @Autowired
    private AlumniFollowMapper alumniFollowMapper;

    /**
     * 查询校友关注
     * 
     * @param id 校友关注主键
     * @return 校友关注
     */
    @Override
    public AlumniFollow selectAlumniFollowById(Long id)
    {
        return alumniFollowMapper.selectAlumniFollowById(id);
    }

    /**
     * 查询校友关注列表
     * 
     * @param alumniFollow 校友关注
     * @return 校友关注
     */
    @Override
    public List<AlumniFollow> selectAlumniFollowList(AlumniFollow alumniFollow)
    {
        return alumniFollowMapper.selectAlumniFollowList(alumniFollow);
    }

    /**
     * 新增校友关注
     * 
     * @param alumniFollow 校友关注
     * @return 结果
     */
    @Override
    public int insertAlumniFollow(AlumniFollow alumniFollow)
    {
        alumniFollow.setCreateTime(DateUtils.getNowDate());
        return alumniFollowMapper.insertAlumniFollow(alumniFollow);
    }

    /**
     * 修改校友关注
     * 
     * @param alumniFollow 校友关注
     * @return 结果
     */
    @Override
    public int updateAlumniFollow(AlumniFollow alumniFollow)
    {
        return alumniFollowMapper.updateAlumniFollow(alumniFollow);
    }

    /**
     * 批量删除校友关注
     * 
     * @param ids 需要删除的校友关注主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFollowByIds(Long[] ids)
    {
        return alumniFollowMapper.deleteAlumniFollowByIds(ids);
    }

    /**
     * 删除校友关注信息
     * 
     * @param id 校友关注主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFollowById(Long id)
    {
        return alumniFollowMapper.deleteAlumniFollowById(id);
    }
}
