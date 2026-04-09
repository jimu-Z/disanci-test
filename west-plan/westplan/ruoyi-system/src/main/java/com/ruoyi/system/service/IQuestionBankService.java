package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QuestionBank;

/**
 * 题库信息Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IQuestionBankService 
{
    /**
     * 查询题库信息
     * 
     * @param id 题库信息主键
     * @return 题库信息
     */
    QuestionBank selectQuestionBankById(Long id);

    /**
     * 查询题库信息列表
     * 
     * @param questionBank 题库信息
     * @return 题库信息集合
     */
    List<QuestionBank> selectQuestionBankList(QuestionBank questionBank);

    /**
     * 新增题库信息
     * 
     * @param questionBank 题库信息
     * @return 结果
     */
    int insertQuestionBank(QuestionBank questionBank);

    /**
     * 修改题库信息
     * 
     * @param questionBank 题库信息
     * @return 结果
     */
    int updateQuestionBank(QuestionBank questionBank);

    /**
     * 批量删除题库信息
     * 
     * @param ids 需要删除的题库信息主键集合
     * @return 结果
     */
    int deleteQuestionBankByIds(Long[] ids);

    /**
     * 删除题库信息信息
     * 
     * @param id 题库信息主键
     * @return 结果
     */
    int deleteQuestionBankById(Long id);
}
