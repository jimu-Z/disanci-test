package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WxMpConfig;

/**
 * 微信公众号配置Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface WxMpConfigMapper 
{
    /**
     * 查询微信公众号配置
     * 
     * @param id 微信公众号配置主键
     * @return 微信公众号配置
     */
    WxMpConfig selectWxMpConfigById(Long id);

    /**
     * 查询微信公众号配置列表
     * 
     * @param wxMpConfig 微信公众号配置
     * @return 微信公众号配置集合
     */
    List<WxMpConfig> selectWxMpConfigList(WxMpConfig wxMpConfig);

    /**
     * 新增微信公众号配置
     * 
     * @param wxMpConfig 微信公众号配置
     * @return 结果
     */
    int insertWxMpConfig(WxMpConfig wxMpConfig);

    /**
     * 修改微信公众号配置
     * 
     * @param wxMpConfig 微信公众号配置
     * @return 结果
     */
    int updateWxMpConfig(WxMpConfig wxMpConfig);

    /**
     * 删除微信公众号配置
     * 
     * @param id 微信公众号配置主键
     * @return 结果
     */
    int deleteWxMpConfigById(Long id);

    /**
     * 批量删除微信公众号配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWxMpConfigByIds(Long[] ids);
}
