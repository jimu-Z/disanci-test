package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ContentAudit;

/**
 * 内容审核记录Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IContentAuditService 
{
    /**
     * 查询内容审核记录
     * 
     * @param id 内容审核记录主键
     * @return 内容审核记录
     */
    ContentAudit selectContentAuditById(Long id);

    /**
     * 查询内容审核记录列表
     * 
     * @param contentAudit 内容审核记录
     * @return 内容审核记录集合
     */
    List<ContentAudit> selectContentAuditList(ContentAudit contentAudit);

    /**
     * 新增内容审核记录
     * 
     * @param contentAudit 内容审核记录
     * @return 结果
     */
    int insertContentAudit(ContentAudit contentAudit);

    /**
     * 修改内容审核记录
     * 
     * @param contentAudit 内容审核记录
     * @return 结果
     */
    int updateContentAudit(ContentAudit contentAudit);

    /**
     * 批量删除内容审核记录
     * 
     * @param ids 需要删除的内容审核记录主键集合
     * @return 结果
     */
    int deleteContentAuditByIds(Long[] ids);

    /**
     * 删除内容审核记录信息
     * 
     * @param id 内容审核记录主键
     * @return 结果
     */
    int deleteContentAuditById(Long id);
}
