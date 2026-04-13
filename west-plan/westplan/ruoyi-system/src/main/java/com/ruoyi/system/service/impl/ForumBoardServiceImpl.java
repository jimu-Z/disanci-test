package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ForumBoardMapper;
import com.ruoyi.system.domain.ForumBoard;
import com.ruoyi.system.service.IForumBoardService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 论坛板块Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class ForumBoardServiceImpl implements IForumBoardService 
{
    @Resource
    private ForumBoardMapper forumBoardMapper;

    @Override
    public List<ForumBoard> selectForumBoardList(ForumBoard forumBoard) {
        return forumBoardMapper.selectForumBoardList(forumBoard);
    }

    @Override
    public List<ForumBoard> selectEnableBoardList() {
        return forumBoardMapper.selectEnableBoardList();
    }

    @Override
    public ForumBoard selectForumBoardById(Long id) {
        return forumBoardMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertForumBoard(ForumBoard forumBoard) {
        // 校验板块名称唯一
        if (forumBoardMapper.countByBoardName(forumBoard.getBoardName(), 0L) > 0) {
            throw new ServiceException("板块名称已存在，请更换");
        }
        return forumBoardMapper.insertSelective(forumBoard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateForumBoard(ForumBoard forumBoard) {
        // 校验板块名称唯一（排除自身）
        if (forumBoardMapper.countByBoardName(forumBoard.getBoardName(), forumBoard.getId()) > 0) {
            throw new ServiceException("板块名称已存在，请更换");
        }
        return forumBoardMapper.updateByPrimaryKeySelective(forumBoard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBoardSort(List<ForumBoard> boards) {
        return forumBoardMapper.updateBoardSort(boards);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteForumBoardByIds(Long[] ids) {
        // 校验板块下是否有帖子
        if (forumBoardMapper.countPostByBoardIds(ids) > 0) {
            throw new ServiceException("所选板块下存在帖子，无法删除");
        }
        int count = 0;
        for (Long id : ids) {
            count += forumBoardMapper.deleteByPrimaryKey(id);
        }
        return count;
    }

    @Override
    public int deleteForumBoardById(Long id) {
        if (id == null) {
            throw new ServiceException("板块ID不能为空");
        }
        if (forumBoardMapper.countPostByBoardIds(new Long[] { id }) > 0) {
            throw new ServiceException("板块下存在帖子，无法删除");
        }
        return forumBoardMapper.deleteByPrimaryKey(id);
    }
}
