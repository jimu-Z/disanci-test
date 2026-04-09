package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AlumniFeelingComment;

/**
 * 感悟评论Service接口
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
public interface IAlumniFeelingCommentService 
{
    /**
     * 查询感悟评论
     * 
     * @param id 感悟评论主键
     * @return 感悟评论
     */
    AlumniFeelingComment selectAlumniFeelingCommentById(Long id);

    /**
     * 查询感悟评论列表
     * 
     * @param alumniFeelingComment 感悟评论
     * @return 感悟评论集合
     */
    List<AlumniFeelingComment> selectAlumniFeelingCommentList(AlumniFeelingComment alumniFeelingComment);

    /**
     * 新增感悟评论
     * 
     * @param alumniFeelingComment 感悟评论
     * @return 结果
     */
    int insertAlumniFeelingComment(AlumniFeelingComment alumniFeelingComment);

    /**
     * 修改感悟评论
     * 
     * @param alumniFeelingComment 感悟评论
     * @return 结果
     */
    int updateAlumniFeelingComment(AlumniFeelingComment alumniFeelingComment);

    /**
     * 批量删除感悟评论
     * 
     * @param ids 需要删除的感悟评论主键集合
     * @return 结果
     */
    int deleteAlumniFeelingCommentByIds(Long[] ids);

    /**
     * 删除感悟评论信息
     * 
     * @param id 感悟评论主键
     * @return 结果
     */
    int deleteAlumniFeelingCommentById(Long id);
}
