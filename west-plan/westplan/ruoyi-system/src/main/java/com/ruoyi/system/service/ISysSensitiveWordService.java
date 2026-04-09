package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysSensitiveWord;

/**
 * 敏感词库Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface ISysSensitiveWordService 
{
    /**
     * 查询敏感词库
     * 
     * @param id 敏感词库主键
     * @return 敏感词库
     */
    SysSensitiveWord selectSysSensitiveWordById(Long id);

    /**
     * 查询敏感词库列表
     * 
     * @param sysSensitiveWord 敏感词库
     * @return 敏感词库集合
     */
    List<SysSensitiveWord> selectSysSensitiveWordList(SysSensitiveWord sysSensitiveWord);

    /**
     * 新增敏感词库
     * 
     * @param sysSensitiveWord 敏感词库
     * @return 结果
     */
    int insertSysSensitiveWord(SysSensitiveWord sysSensitiveWord);

    /**
     * 修改敏感词库
     * 
     * @param sysSensitiveWord 敏感词库
     * @return 结果
     */
    int updateSysSensitiveWord(SysSensitiveWord sysSensitiveWord);

    /**
     * 批量删除敏感词库
     * 
     * @param ids 需要删除的敏感词库主键集合
     * @return 结果
     */
    int deleteSysSensitiveWordByIds(Long[] ids);

    /**
     * 删除敏感词库信息
     * 
     * @param id 敏感词库主键
     * @return 结果
     */
    int deleteSysSensitiveWordById(Long id);
}
