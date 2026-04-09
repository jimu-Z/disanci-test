package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniFeelingReplyMapper;
import com.ruoyi.system.domain.AlumniFeelingReply;
import com.ruoyi.system.service.IAlumniFeelingReplyService;

/**
 * 评论回复Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
@Service
public class AlumniFeelingReplyServiceImpl implements IAlumniFeelingReplyService 
{
    @Autowired
    private AlumniFeelingReplyMapper alumniFeelingReplyMapper;

    /**
     * 查询评论回复
     * 
     * @param id 评论回复主键
     * @return 评论回复
     */
    @Override
    public AlumniFeelingReply selectAlumniFeelingReplyById(Long id)
    {
        return alumniFeelingReplyMapper.selectAlumniFeelingReplyById(id);
    }

    /**
     * 查询评论回复列表
     * 
     * @param alumniFeelingReply 评论回复
     * @return 评论回复
     */
    @Override
    public List<AlumniFeelingReply> selectAlumniFeelingReplyList(AlumniFeelingReply alumniFeelingReply)
    {
        return alumniFeelingReplyMapper.selectAlumniFeelingReplyList(alumniFeelingReply);
    }

    /**
     * 新增评论回复
     * 
     * @param alumniFeelingReply 评论回复
     * @return 结果
     */
    @Override
    public int insertAlumniFeelingReply(AlumniFeelingReply alumniFeelingReply)
    {
        alumniFeelingReply.setCreateTime(DateUtils.getNowDate());
        return alumniFeelingReplyMapper.insertAlumniFeelingReply(alumniFeelingReply);
    }

    /**
     * 修改评论回复
     * 
     * @param alumniFeelingReply 评论回复
     * @return 结果
     */
    @Override
    public int updateAlumniFeelingReply(AlumniFeelingReply alumniFeelingReply)
    {
        return alumniFeelingReplyMapper.updateAlumniFeelingReply(alumniFeelingReply);
    }

    /**
     * 批量删除评论回复
     * 
     * @param ids 需要删除的评论回复主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingReplyByIds(Long[] ids)
    {
        return alumniFeelingReplyMapper.deleteAlumniFeelingReplyByIds(ids);
    }

    /**
     * 删除评论回复信息
     * 
     * @param id 评论回复主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingReplyById(Long id)
    {
        return alumniFeelingReplyMapper.deleteAlumniFeelingReplyById(id);
    }
}
