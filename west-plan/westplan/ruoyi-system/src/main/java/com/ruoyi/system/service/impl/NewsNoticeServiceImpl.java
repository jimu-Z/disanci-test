package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.NewsNoticeMapper;
import com.ruoyi.system.domain.NewsNotice;
import com.ruoyi.system.service.INewsNoticeService;

/**
 * 新闻公告Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class NewsNoticeServiceImpl implements INewsNoticeService 
{
    @Autowired
    private NewsNoticeMapper newsNoticeMapper;

    /**
     * 查询新闻公告
     * 
     * @param id 新闻公告主键
     * @return 新闻公告
     */
    @Override
    public NewsNotice selectNewsNoticeById(Long id)
    {
        return newsNoticeMapper.selectNewsNoticeById(id);
    }

    /**
     * 查询新闻公告列表
     * 
     * @param newsNotice 新闻公告
     * @return 新闻公告
     */
    @Override
    public List<NewsNotice> selectNewsNoticeList(NewsNotice newsNotice)
    {
        return newsNoticeMapper.selectNewsNoticeList(newsNotice);
    }

    /**
     * 新增新闻公告
     * 
     * @param newsNotice 新闻公告
     * @return 结果
     */
    @Override
    public int insertNewsNotice(NewsNotice newsNotice)
    {
        newsNotice.setCreateTime(DateUtils.getNowDate());
        return newsNoticeMapper.insertNewsNotice(newsNotice);
    }

    /**
     * 修改新闻公告
     * 
     * @param newsNotice 新闻公告
     * @return 结果
     */
    @Override
    public int updateNewsNotice(NewsNotice newsNotice)
    {
        newsNotice.setUpdateTime(DateUtils.getNowDate());
        return newsNoticeMapper.updateNewsNotice(newsNotice);
    }

    /**
     * 批量删除新闻公告
     * 
     * @param ids 需要删除的新闻公告主键
     * @return 结果
     */
    @Override
    public int deleteNewsNoticeByIds(Long[] ids)
    {
        return newsNoticeMapper.deleteNewsNoticeByIds(ids);
    }

    /**
     * 删除新闻公告信息
     * 
     * @param id 新闻公告主键
     * @return 结果
     */
    @Override
    public int deleteNewsNoticeById(Long id)
    {
        return newsNoticeMapper.deleteNewsNoticeById(id);
    }
}
