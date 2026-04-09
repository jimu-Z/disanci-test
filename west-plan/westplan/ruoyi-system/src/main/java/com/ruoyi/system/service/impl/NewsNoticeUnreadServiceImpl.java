package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NewsNoticeUnreadMapper;
import com.ruoyi.system.domain.NewsNoticeUnread;
import com.ruoyi.system.service.INewsNoticeUnreadService;

/**
 * 公告未读记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class NewsNoticeUnreadServiceImpl implements INewsNoticeUnreadService 
{
    @Autowired
    private NewsNoticeUnreadMapper newsNoticeUnreadMapper;

    /**
     * 查询公告未读记录
     * 
     * @param id 公告未读记录主键
     * @return 公告未读记录
     */
    @Override
    public NewsNoticeUnread selectNewsNoticeUnreadById(Long id)
    {
        return newsNoticeUnreadMapper.selectNewsNoticeUnreadById(id);
    }

    /**
     * 查询公告未读记录列表
     * 
     * @param newsNoticeUnread 公告未读记录
     * @return 公告未读记录
     */
    @Override
    public List<NewsNoticeUnread> selectNewsNoticeUnreadList(NewsNoticeUnread newsNoticeUnread)
    {
        return newsNoticeUnreadMapper.selectNewsNoticeUnreadList(newsNoticeUnread);
    }

    /**
     * 新增公告未读记录
     * 
     * @param newsNoticeUnread 公告未读记录
     * @return 结果
     */
    @Override
    public int insertNewsNoticeUnread(NewsNoticeUnread newsNoticeUnread)
    {
        newsNoticeUnread.setCreateTime(DateUtils.getNowDate());
        return newsNoticeUnreadMapper.insertNewsNoticeUnread(newsNoticeUnread);
    }

    /**
     * 修改公告未读记录
     * 
     * @param newsNoticeUnread 公告未读记录
     * @return 结果
     */
    @Override
    public int updateNewsNoticeUnread(NewsNoticeUnread newsNoticeUnread)
    {
        return newsNoticeUnreadMapper.updateNewsNoticeUnread(newsNoticeUnread);
    }

    /**
     * 批量删除公告未读记录
     * 
     * @param ids 需要删除的公告未读记录主键
     * @return 结果
     */
    @Override
    public int deleteNewsNoticeUnreadByIds(Long[] ids)
    {
        return newsNoticeUnreadMapper.deleteNewsNoticeUnreadByIds(ids);
    }

    /**
     * 删除公告未读记录信息
     * 
     * @param id 公告未读记录主键
     * @return 结果
     */
    @Override
    public int deleteNewsNoticeUnreadById(Long id)
    {
        return newsNoticeUnreadMapper.deleteNewsNoticeUnreadById(id);
    }
}
