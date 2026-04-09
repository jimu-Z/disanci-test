package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ForumBoard;

/**
 * 论坛板块Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface IForumBoardService 
{
    /**
     * 查询论坛板块
     * 
     * @param id 论坛板块主键
     * @return 论坛板块
     */
    ForumBoard selectForumBoardById(Long id);

    /**
     * 查询论坛板块列表
     * 
     * @param forumBoard 论坛板块
     * @return 论坛板块集合
     */
    List<ForumBoard> selectForumBoardList(ForumBoard forumBoard);

    /**
     * 新增论坛板块
     * 
     * @param forumBoard 论坛板块
     * @return 结果
     */
    int insertForumBoard(ForumBoard forumBoard);

    /**
     * 修改论坛板块
     * 
     * @param forumBoard 论坛板块
     * @return 结果
     */
    int updateForumBoard(ForumBoard forumBoard);

    /**
     * 批量删除论坛板块
     * 
     * @param ids 需要删除的论坛板块主键集合
     * @return 结果
     */
    int deleteForumBoardByIds(Long[] ids);

    /**
     * 删除论坛板块信息
     * 
     * @param id 论坛板块主键
     * @return 结果
     */
    int deleteForumBoardById(Long id);
    /**
     * 批量更新板块排序
     */
    int updateBoardSort(List<ForumBoard> boards);
    /**
     * 查询启用的板块列表（前端展示）
     */
    List<ForumBoard> selectEnableBoardList();
}
