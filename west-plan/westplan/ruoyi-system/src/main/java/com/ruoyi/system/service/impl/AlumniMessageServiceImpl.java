package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniMessageMapper;
import com.ruoyi.system.domain.AlumniMessage;
import com.ruoyi.system.service.IAlumniMessageService;

/**
 * 校友私信Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class AlumniMessageServiceImpl implements IAlumniMessageService 
{
    @Autowired
    private AlumniMessageMapper alumniMessageMapper;

    /**
     * 查询校友私信
     * 
     * @param id 校友私信主键
     * @return 校友私信
     */
    @Override
    public AlumniMessage selectAlumniMessageById(Long id)
    {
        return alumniMessageMapper.selectAlumniMessageById(id);
    }

    /**
     * 查询校友私信列表
     * 
     * @param alumniMessage 校友私信
     * @return 校友私信
     */
    @Override
    public List<AlumniMessage> selectAlumniMessageList(AlumniMessage alumniMessage)
    {
        return alumniMessageMapper.selectAlumniMessageList(alumniMessage);
    }

    /**
     * 新增校友私信
     * 
     * @param alumniMessage 校友私信
     * @return 结果
     */
    @Override
    public int insertAlumniMessage(AlumniMessage alumniMessage)
    {
        return alumniMessageMapper.insertAlumniMessage(alumniMessage);
    }

    /**
     * 修改校友私信
     * 
     * @param alumniMessage 校友私信
     * @return 结果
     */
    @Override
    public int updateAlumniMessage(AlumniMessage alumniMessage)
    {
        return alumniMessageMapper.updateAlumniMessage(alumniMessage);
    }

    /**
     * 批量删除校友私信
     * 
     * @param ids 需要删除的校友私信主键
     * @return 结果
     */
    @Override
    public int deleteAlumniMessageByIds(Long[] ids)
    {
        return alumniMessageMapper.deleteAlumniMessageByIds(ids);
    }

    /**
     * 删除校友私信信息
     * 
     * @param id 校友私信主键
     * @return 结果
     */
    @Override
    public int deleteAlumniMessageById(Long id)
    {
        return alumniMessageMapper.deleteAlumniMessageById(id);
    }
}
