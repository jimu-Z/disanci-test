package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AlumniFeelingCommentMapper;
import com.ruoyi.system.domain.AlumniFeelingComment;
import com.ruoyi.system.service.IAlumniFeelingCommentService;

/**
 * 感悟评论Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-21
 */
@Service
public class AlumniFeelingCommentServiceImpl implements IAlumniFeelingCommentService 
{
    @Autowired
    private AlumniFeelingCommentMapper alumniFeelingCommentMapper;

    /**
     * 查询感悟评论
     * 
     * @param id 感悟评论主键
     * @return 感悟评论
     */
    @Override
    public AlumniFeelingComment selectAlumniFeelingCommentById(Long id)
    {
        return alumniFeelingCommentMapper.selectAlumniFeelingCommentById(id);
    }

    /**
     * 查询感悟评论列表
     * 
     * @param alumniFeelingComment 感悟评论
     * @return 感悟评论
     */
    @Override
    public List<AlumniFeelingComment> selectAlumniFeelingCommentList(AlumniFeelingComment alumniFeelingComment)
    {
        return alumniFeelingCommentMapper.selectAlumniFeelingCommentList(alumniFeelingComment);
    }

    /**
     * 新增感悟评论
     * 
     * @param alumniFeelingComment 感悟评论
     * @return 结果
     */
    @Override
    public int insertAlumniFeelingComment(AlumniFeelingComment alumniFeelingComment)
    {
        alumniFeelingComment.setCreateTime(DateUtils.getNowDate());
        return alumniFeelingCommentMapper.insertAlumniFeelingComment(alumniFeelingComment);
    }

    /**
     * 修改感悟评论
     * 
     * @param alumniFeelingComment 感悟评论
     * @return 结果
     */
    @Override
    public int updateAlumniFeelingComment(AlumniFeelingComment alumniFeelingComment)
    {
        return alumniFeelingCommentMapper.updateAlumniFeelingComment(alumniFeelingComment);
    }

    /**
     * 批量删除感悟评论
     * 
     * @param ids 需要删除的感悟评论主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingCommentByIds(Long[] ids)
    {
        return alumniFeelingCommentMapper.deleteAlumniFeelingCommentByIds(ids);
    }

    /**
     * 删除感悟评论信息
     * 
     * @param id 感悟评论主键
     * @return 结果
     */
    @Override
    public int deleteAlumniFeelingCommentById(Long id)
    {
        return alumniFeelingCommentMapper.deleteAlumniFeelingCommentById(id);
    }
}
