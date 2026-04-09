package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ForumBoard;
import org.apache.ibatis.annotations.Param;
/**
 * 论坛板块Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface ForumBoardMapper 
{
    int deleteByPrimaryKey(Long id);
    int insert(ForumBoard record);
    int insertSelective(ForumBoard record);
    ForumBoard selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(ForumBoard record);
    int updateByPrimaryKey(ForumBoard record);

    // 自定义方法
    /** 查询所有板块（带条件） */
    List<ForumBoard> selectForumBoardList(ForumBoard forumBoard);
    /** 查询启用的板块列表（前端展示） */
    List<ForumBoard> selectEnableBoardList();
    /** 批量更新排序 */
    int updateBoardSort(@Param("boards") List<ForumBoard> boards);
    /** 根据板块名称查询（校验唯一） */
    int countByBoardName(@Param("boardName") String boardName, @Param("excludeId") Long excludeId);
    /** 校验板块下是否有帖子 */
    int countPostByBoardIds(@Param("ids") Long[] ids);
}
