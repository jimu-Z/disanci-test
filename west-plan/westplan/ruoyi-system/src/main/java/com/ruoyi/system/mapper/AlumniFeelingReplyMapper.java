package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AlumniFeelingReply;
import org.apache.ibatis.annotations.Param;

/**
 * 评论回复Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public interface AlumniFeelingReplyMapper 
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
     * 删除评论回复
     * 
     * @param id 评论回复主键
     * @return 结果
     */
    int deleteAlumniFeelingReplyById(Long id);

    /**
     * 批量删除评论回复
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAlumniFeelingReplyByIds(Long[] ids);
    /**
     * 根据评论ID查询回复列表
     */
    List<AlumniFeelingReply> selectReplyByCommentId(@Param("commentId") Long commentId);
}
