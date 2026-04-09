package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniStyleMapper;
import com.ruoyi.system.domain.AlumniStyle;
import com.ruoyi.system.service.IAlumniStyleService;

/**
 * 校友个人风采Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class AlumniStyleServiceImpl implements IAlumniStyleService 
{
    @Autowired
    private AlumniStyleMapper alumniStyleMapper;

    /**
     * 查询校友个人风采
     * 
     * @param id 校友个人风采主键
     * @return 校友个人风采
     */
    @Override
    public AlumniStyle selectAlumniStyleById(Long id)
    {
        return alumniStyleMapper.selectAlumniStyleById(id);
    }

    /**
     * 查询校友个人风采列表
     * 
     * @param alumniStyle 校友个人风采
     * @return 校友个人风采
     */
    @Override
    public List<AlumniStyle> selectAlumniStyleList(AlumniStyle alumniStyle)
    {
        return alumniStyleMapper.selectAlumniStyleList(alumniStyle);
    }

    /**
     * 新增校友个人风采
     * 
     * @param alumniStyle 校友个人风采
     * @return 结果
     */
    @Override
    public int insertAlumniStyle(AlumniStyle alumniStyle)
    {
        alumniStyle.setCreateTime(DateUtils.getNowDate());
        return alumniStyleMapper.insertAlumniStyle(alumniStyle);
    }

    /**
     * 修改校友个人风采
     * 
     * @param alumniStyle 校友个人风采
     * @return 结果
     */
    @Override
    public int updateAlumniStyle(AlumniStyle alumniStyle)
    {
        alumniStyle.setUpdateTime(DateUtils.getNowDate());
        return alumniStyleMapper.updateAlumniStyle(alumniStyle);
    }

    /**
     * 批量删除校友个人风采
     * 
     * @param ids 需要删除的校友个人风采主键
     * @return 结果
     */
    @Override
    public int deleteAlumniStyleByIds(Long[] ids)
    {
        return alumniStyleMapper.deleteAlumniStyleByIds(ids);
    }

    /**
     * 删除校友个人风采信息
     * 
     * @param id 校友个人风采主键
     * @return 结果
     */
    @Override
    public int deleteAlumniStyleById(Long id)
    {
        return alumniStyleMapper.deleteAlumniStyleById(id);
    }
}
