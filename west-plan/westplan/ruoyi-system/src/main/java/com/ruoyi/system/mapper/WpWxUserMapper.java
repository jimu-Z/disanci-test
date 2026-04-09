package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WpWxUser;

/**
 * 微信用户绑定Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface WpWxUserMapper 
{
    /**
     * 查询微信用户绑定
     * 
     * @param id 微信用户绑定主键
     * @return 微信用户绑定
     */
    WpWxUser selectWpWxUserById(Long id);

    /**
     * 查询微信用户绑定列表
     * 
     * @param wpWxUser 微信用户绑定
     * @return 微信用户绑定集合
     */
    List<WpWxUser> selectWpWxUserList(WpWxUser wpWxUser);

    /**
     * 新增微信用户绑定
     * 
     * @param wpWxUser 微信用户绑定
     * @return 结果
     */
    int insertWpWxUser(WpWxUser wpWxUser);

    /**
     * 修改微信用户绑定
     * 
     * @param wpWxUser 微信用户绑定
     * @return 结果
     */
    int updateWpWxUser(WpWxUser wpWxUser);

    /**
     * 删除微信用户绑定
     * 
     * @param id 微信用户绑定主键
     * @return 结果
     */
    int deleteWpWxUserById(Long id);

    /**
     * 批量删除微信用户绑定
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWpWxUserByIds(Long[] ids);
}
