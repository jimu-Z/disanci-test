package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AlumniFollow;

/**
 * 校友关注Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface AlumniFollowMapper 
{
    /**
     * 查询校友关注
     * 
     * @param id 校友关注主键
     * @return 校友关注
     */
    AlumniFollow selectAlumniFollowById(Long id);

    /**
     * 查询校友关注列表
     * 
     * @param alumniFollow 校友关注
     * @return 校友关注集合
     */
    List<AlumniFollow> selectAlumniFollowList(AlumniFollow alumniFollow);

    /**
     * 新增校友关注
     * 
     * @param alumniFollow 校友关注
     * @return 结果
     */
    int insertAlumniFollow(AlumniFollow alumniFollow);

    /**
     * 修改校友关注
     * 
     * @param alumniFollow 校友关注
     * @return 结果
     */
    int updateAlumniFollow(AlumniFollow alumniFollow);

    /**
     * 删除校友关注
     * 
     * @param id 校友关注主键
     * @return 结果
     */
    int deleteAlumniFollowById(Long id);

    /**
     * 批量删除校友关注
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAlumniFollowByIds(Long[] ids);
}
