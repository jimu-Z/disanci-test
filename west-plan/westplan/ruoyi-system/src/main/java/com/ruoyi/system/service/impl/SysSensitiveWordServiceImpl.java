package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysSensitiveWordMapper;
import com.ruoyi.system.domain.SysSensitiveWord;
import com.ruoyi.system.service.ISysSensitiveWordService;

/**
 * 敏感词库Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class SysSensitiveWordServiceImpl implements ISysSensitiveWordService 
{
    @Autowired
    private SysSensitiveWordMapper sysSensitiveWordMapper;

    /**
     * 查询敏感词库
     * 
     * @param id 敏感词库主键
     * @return 敏感词库
     */
    @Override
    public SysSensitiveWord selectSysSensitiveWordById(Long id)
    {
        return sysSensitiveWordMapper.selectSysSensitiveWordById(id);
    }

    /**
     * 查询敏感词库列表
     * 
     * @param sysSensitiveWord 敏感词库
     * @return 敏感词库
     */
    @Override
    public List<SysSensitiveWord> selectSysSensitiveWordList(SysSensitiveWord sysSensitiveWord)
    {
        return sysSensitiveWordMapper.selectSysSensitiveWordList(sysSensitiveWord);
    }

    /**
     * 新增敏感词库
     * 
     * @param sysSensitiveWord 敏感词库
     * @return 结果
     */
    @Override
    public int insertSysSensitiveWord(SysSensitiveWord sysSensitiveWord)
    {
        sysSensitiveWord.setCreateTime(DateUtils.getNowDate());
        return sysSensitiveWordMapper.insertSysSensitiveWord(sysSensitiveWord);
    }

    /**
     * 修改敏感词库
     * 
     * @param sysSensitiveWord 敏感词库
     * @return 结果
     */
    @Override
    public int updateSysSensitiveWord(SysSensitiveWord sysSensitiveWord)
    {
        sysSensitiveWord.setUpdateTime(DateUtils.getNowDate());
        return sysSensitiveWordMapper.updateSysSensitiveWord(sysSensitiveWord);
    }

    /**
     * 批量删除敏感词库
     * 
     * @param ids 需要删除的敏感词库主键
     * @return 结果
     */
    @Override
    public int deleteSysSensitiveWordByIds(Long[] ids)
    {
        return sysSensitiveWordMapper.deleteSysSensitiveWordByIds(ids);
    }

    /**
     * 删除敏感词库信息
     * 
     * @param id 敏感词库主键
     * @return 结果
     */
    @Override
    public int deleteSysSensitiveWordById(Long id)
    {
        return sysSensitiveWordMapper.deleteSysSensitiveWordById(id);
    }
}
