package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysFriendLink;

/**
 * 友情链接Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface ISysFriendLinkService 
{
    /**
     * 查询友情链接
     * 
     * @param id 友情链接主键
     * @return 友情链接
     */
    SysFriendLink selectSysFriendLinkById(Long id);

    /**
     * 查询友情链接列表
     * 
     * @param sysFriendLink 友情链接
     * @return 友情链接集合
     */
    List<SysFriendLink> selectSysFriendLinkList(SysFriendLink sysFriendLink);

    /**
     * 新增友情链接
     * 
     * @param sysFriendLink 友情链接
     * @return 结果
     */
    int insertSysFriendLink(SysFriendLink sysFriendLink);

    /**
     * 修改友情链接
     * 
     * @param sysFriendLink 友情链接
     * @return 结果
     */
    int updateSysFriendLink(SysFriendLink sysFriendLink);

    /**
     * 批量删除友情链接
     * 
     * @param ids 需要删除的友情链接主键集合
     * @return 结果
     */
    int deleteSysFriendLinkByIds(Long[] ids);

    /**
     * 删除友情链接信息
     * 
     * @param id 友情链接主键
     * @return 结果
     */
    int deleteSysFriendLinkById(Long id);
}
