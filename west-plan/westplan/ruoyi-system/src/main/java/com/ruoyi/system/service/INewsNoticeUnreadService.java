package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.NewsNoticeUnread;

/**
 * 公告未读记录Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface INewsNoticeUnreadService 
{
    /**
     * 查询公告未读记录
     * 
     * @param id 公告未读记录主键
     * @return 公告未读记录
     */
    NewsNoticeUnread selectNewsNoticeUnreadById(Long id);

    /**
     * 查询公告未读记录列表
     * 
     * @param newsNoticeUnread 公告未读记录
     * @return 公告未读记录集合
     */
    List<NewsNoticeUnread> selectNewsNoticeUnreadList(NewsNoticeUnread newsNoticeUnread);

    /**
     * 新增公告未读记录
     * 
     * @param newsNoticeUnread 公告未读记录
     * @return 结果
     */
    int insertNewsNoticeUnread(NewsNoticeUnread newsNoticeUnread);

    /**
     * 修改公告未读记录
     * 
     * @param newsNoticeUnread 公告未读记录
     * @return 结果
     */
    int updateNewsNoticeUnread(NewsNoticeUnread newsNoticeUnread);

    /**
     * 批量删除公告未读记录
     * 
     * @param ids 需要删除的公告未读记录主键集合
     * @return 结果
     */
    int deleteNewsNoticeUnreadByIds(Long[] ids);

    /**
     * 删除公告未读记录信息
     * 
     * @param id 公告未读记录主键
     * @return 结果
     */
    int deleteNewsNoticeUnreadById(Long id);
}
