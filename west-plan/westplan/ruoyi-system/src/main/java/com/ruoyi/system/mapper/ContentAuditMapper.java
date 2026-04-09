package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ContentAudit;

/**
 * 内容审核记录Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface ContentAuditMapper 
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
     * 删除内容审核记录
     * 
     * @param id 内容审核记录主键
     * @return 结果
     */
    int deleteContentAuditById(Long id);

    /**
     * 批量删除内容审核记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteContentAuditByIds(Long[] ids);
}
