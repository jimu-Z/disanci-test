package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ContentAuditMapper;
import com.ruoyi.system.domain.ContentAudit;
import com.ruoyi.system.service.IContentAuditService;

/**
 * 内容审核记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class ContentAuditServiceImpl implements IContentAuditService 
{
    @Autowired
    private ContentAuditMapper contentAuditMapper;

    /**
     * 查询内容审核记录
     * 
     * @param id 内容审核记录主键
     * @return 内容审核记录
     */
    @Override
    public ContentAudit selectContentAuditById(Long id)
    {
        return contentAuditMapper.selectContentAuditById(id);
    }

    /**
     * 查询内容审核记录列表
     * 
     * @param contentAudit 内容审核记录
     * @return 内容审核记录
     */
    @Override
    public List<ContentAudit> selectContentAuditList(ContentAudit contentAudit)
    {
        return contentAuditMapper.selectContentAuditList(contentAudit);
    }

    /**
     * 新增内容审核记录
     * 
     * @param contentAudit 内容审核记录
     * @return 结果
     */
    @Override
    public int insertContentAudit(ContentAudit contentAudit)
    {
        contentAudit.setCreateTime(DateUtils.getNowDate());
        return contentAuditMapper.insertContentAudit(contentAudit);
    }

    /**
     * 修改内容审核记录
     * 
     * @param contentAudit 内容审核记录
     * @return 结果
     */
    @Override
    public int updateContentAudit(ContentAudit contentAudit)
    {
        return contentAuditMapper.updateContentAudit(contentAudit);
    }

    /**
     * 批量删除内容审核记录
     * 
     * @param ids 需要删除的内容审核记录主键
     * @return 结果
     */
    @Override
    public int deleteContentAuditByIds(Long[] ids)
    {
        return contentAuditMapper.deleteContentAuditByIds(ids);
    }

    /**
     * 删除内容审核记录信息
     * 
     * @param id 内容审核记录主键
     * @return 结果
     */
    @Override
    public int deleteContentAuditById(Long id)
    {
        return contentAuditMapper.deleteContentAuditById(id);
    }
}
