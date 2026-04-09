package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AlumniFeelingReply;

/**
 * 评论回复Service接口
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public interface IAlumniFeelingReplyService 
{
    /**
     * 查询评论回复
     * 
     * @param id 评论回复主键
     * @return 评论回复
     */
    AlumniFeelingReply selectAlumniFeelingReplyById(Long id);

    /**
     * 查询评论回复列表
     * 
     * @param alumniFeelingReply 评论回复
     * @return 评论回复集合
     */
    List<AlumniFeelingReply> selectAlumniFeelingReplyList(AlumniFeelingReply alumniFeelingReply);

    /**
     * 新增评论回复
     * 
     * @param alumniFeelingReply 评论回复
     * @return 结果
     */
    int insertAlumniFeelingReply(AlumniFeelingReply alumniFeelingReply);

    /**
     * 修改评论回复
     * 
     * @param alumniFeelingReply 评论回复
     * @return 结果
     */
    int updateAlumniFeelingReply(AlumniFeelingReply alumniFeelingReply);

    /**
     * 批量删除评论回复
     * 
     * @param ids 需要删除的评论回复主键集合
     * @return 结果
     */
    int deleteAlumniFeelingReplyByIds(Long[] ids);

    /**
     * 删除评论回复信息
     * 
     * @param id 评论回复主键
     * @return 结果
     */
    int deleteAlumniFeelingReplyById(Long id);
}
