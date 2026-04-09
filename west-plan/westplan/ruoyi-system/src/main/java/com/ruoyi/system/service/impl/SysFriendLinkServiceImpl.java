package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysFriendLinkMapper;
import com.ruoyi.system.domain.SysFriendLink;
import com.ruoyi.system.service.ISysFriendLinkService;

/**
 * 友情链接Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class SysFriendLinkServiceImpl implements ISysFriendLinkService 
{
    @Autowired
    private SysFriendLinkMapper sysFriendLinkMapper;

    /**
     * 查询友情链接
     * 
     * @param id 友情链接主键
     * @return 友情链接
     */
    @Override
    public SysFriendLink selectSysFriendLinkById(Long id)
    {
        return sysFriendLinkMapper.selectSysFriendLinkById(id);
    }

    /**
     * 查询友情链接列表
     * 
     * @param sysFriendLink 友情链接
     * @return 友情链接
     */
    @Override
    public List<SysFriendLink> selectSysFriendLinkList(SysFriendLink sysFriendLink)
    {
        return sysFriendLinkMapper.selectSysFriendLinkList(sysFriendLink);
    }

    /**
     * 新增友情链接
     * 
     * @param sysFriendLink 友情链接
     * @return 结果
     */
    @Override
    public int insertSysFriendLink(SysFriendLink sysFriendLink)
    {
        sysFriendLink.setCreateTime(DateUtils.getNowDate());
        return sysFriendLinkMapper.insertSysFriendLink(sysFriendLink);
    }

    /**
     * 修改友情链接
     * 
     * @param sysFriendLink 友情链接
     * @return 结果
     */
    @Override
    public int updateSysFriendLink(SysFriendLink sysFriendLink)
    {
        sysFriendLink.setUpdateTime(DateUtils.getNowDate());
        return sysFriendLinkMapper.updateSysFriendLink(sysFriendLink);
    }

    /**
     * 批量删除友情链接
     * 
     * @param ids 需要删除的友情链接主键
     * @return 结果
     */
    @Override
    public int deleteSysFriendLinkByIds(Long[] ids)
    {
        return sysFriendLinkMapper.deleteSysFriendLinkByIds(ids);
    }

    /**
     * 删除友情链接信息
     * 
     * @param id 友情链接主键
     * @return 结果
     */
    @Override
    public int deleteSysFriendLinkById(Long id)
    {
        return sysFriendLinkMapper.deleteSysFriendLinkById(id);
    }
}
