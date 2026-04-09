package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.WpWxUserMapper;
import com.ruoyi.system.domain.WpWxUser;
import com.ruoyi.system.service.IWpWxUserService;

/**
 * 微信用户绑定Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class WpWxUserServiceImpl implements IWpWxUserService 
{
    @Autowired
    private WpWxUserMapper wpWxUserMapper;

    /**
     * 查询微信用户绑定
     * 
     * @param id 微信用户绑定主键
     * @return 微信用户绑定
     */
    @Override
    public WpWxUser selectWpWxUserById(Long id)
    {
        return wpWxUserMapper.selectWpWxUserById(id);
    }

    /**
     * 查询微信用户绑定列表
     * 
     * @param wpWxUser 微信用户绑定
     * @return 微信用户绑定
     */
    @Override
    public List<WpWxUser> selectWpWxUserList(WpWxUser wpWxUser)
    {
        return wpWxUserMapper.selectWpWxUserList(wpWxUser);
    }

    /**
     * 新增微信用户绑定
     * 
     * @param wpWxUser 微信用户绑定
     * @return 结果
     */
    @Override
    public int insertWpWxUser(WpWxUser wpWxUser)
    {
        wpWxUser.setCreateTime(DateUtils.getNowDate());
        return wpWxUserMapper.insertWpWxUser(wpWxUser);
    }

    /**
     * 修改微信用户绑定
     * 
     * @param wpWxUser 微信用户绑定
     * @return 结果
     */
    @Override
    public int updateWpWxUser(WpWxUser wpWxUser)
    {
        wpWxUser.setUpdateTime(DateUtils.getNowDate());
        return wpWxUserMapper.updateWpWxUser(wpWxUser);
    }

    /**
     * 批量删除微信用户绑定
     * 
     * @param ids 需要删除的微信用户绑定主键
     * @return 结果
     */
    @Override
    public int deleteWpWxUserByIds(Long[] ids)
    {
        return wpWxUserMapper.deleteWpWxUserByIds(ids);
    }

    /**
     * 删除微信用户绑定信息
     * 
     * @param id 微信用户绑定主键
     * @return 结果
     */
    @Override
    public int deleteWpWxUserById(Long id)
    {
        return wpWxUserMapper.deleteWpWxUserById(id);
    }
}
