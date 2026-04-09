package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.NewsNotice;

/**
 * 新闻公告Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface NewsNoticeMapper 
{
    /**
     * 查询新闻公告
     * 
     * @param id 新闻公告主键
     * @return 新闻公告
     */
    NewsNotice selectNewsNoticeById(Long id);

    /**
     * 查询新闻公告列表
     * 
     * @param newsNotice 新闻公告
     * @return 新闻公告集合
     */
    List<NewsNotice> selectNewsNoticeList(NewsNotice newsNotice);

    /**
     * 新增新闻公告
     * 
     * @param newsNotice 新闻公告
     * @return 结果
     */
    int insertNewsNotice(NewsNotice newsNotice);

    /**
     * 修改新闻公告
     * 
     * @param newsNotice 新闻公告
     * @return 结果
     */
    int updateNewsNotice(NewsNotice newsNotice);

    /**
     * 删除新闻公告
     * 
     * @param id 新闻公告主键
     * @return 结果
     */
    int deleteNewsNoticeById(Long id);

    /**
     * 批量删除新闻公告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteNewsNoticeByIds(Long[] ids);
}
