package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AlumniMessage;

/**
 * 校友私信Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IAlumniMessageService 
{
    /**
     * 查询校友私信
     * 
     * @param id 校友私信主键
     * @return 校友私信
     */
    AlumniMessage selectAlumniMessageById(Long id);

    /**
     * 查询校友私信列表
     * 
     * @param alumniMessage 校友私信
     * @return 校友私信集合
     */
    List<AlumniMessage> selectAlumniMessageList(AlumniMessage alumniMessage);

    /**
     * 新增校友私信
     * 
     * @param alumniMessage 校友私信
     * @return 结果
     */
    int insertAlumniMessage(AlumniMessage alumniMessage);

    /**
     * 修改校友私信
     * 
     * @param alumniMessage 校友私信
     * @return 结果
     */
    int updateAlumniMessage(AlumniMessage alumniMessage);

    /**
     * 批量删除校友私信
     * 
     * @param ids 需要删除的校友私信主键集合
     * @return 结果
     */
    int deleteAlumniMessageByIds(Long[] ids);

    /**
     * 删除校友私信信息
     * 
     * @param id 校友私信主键
     * @return 结果
     */
    int deleteAlumniMessageById(Long id);
}
