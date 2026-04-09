package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AlumniStyle;

/**
 * 校友个人风采Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface AlumniStyleMapper 
{
    /**
     * 查询校友个人风采
     * 
     * @param id 校友个人风采主键
     * @return 校友个人风采
     */
    AlumniStyle selectAlumniStyleById(Long id);

    /**
     * 查询校友个人风采列表
     * 
     * @param alumniStyle 校友个人风采
     * @return 校友个人风采集合
     */
    List<AlumniStyle> selectAlumniStyleList(AlumniStyle alumniStyle);

    /**
     * 新增校友个人风采
     * 
     * @param alumniStyle 校友个人风采
     * @return 结果
     */
    int insertAlumniStyle(AlumniStyle alumniStyle);

    /**
     * 修改校友个人风采
     * 
     * @param alumniStyle 校友个人风采
     * @return 结果
     */
    int updateAlumniStyle(AlumniStyle alumniStyle);

    /**
     * 删除校友个人风采
     * 
     * @param id 校友个人风采主键
     * @return 结果
     */
    int deleteAlumniStyleById(Long id);

    /**
     * 批量删除校友个人风采
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAlumniStyleByIds(Long[] ids);
}
