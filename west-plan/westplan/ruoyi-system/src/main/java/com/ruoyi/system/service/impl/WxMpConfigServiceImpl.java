package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.WxMpConfigMapper;
import com.ruoyi.system.domain.WxMpConfig;
import com.ruoyi.system.service.IWxMpConfigService;

/**
 * 微信公众号配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class WxMpConfigServiceImpl implements IWxMpConfigService 
{
    @Autowired
    private WxMpConfigMapper wxMpConfigMapper;

    /**
     * 查询微信公众号配置
     * 
     * @param id 微信公众号配置主键
     * @return 微信公众号配置
     */
    @Override
    public WxMpConfig selectWxMpConfigById(Long id)
    {
        return wxMpConfigMapper.selectWxMpConfigById(id);
    }

    /**
     * 查询微信公众号配置列表
     * 
     * @param wxMpConfig 微信公众号配置
     * @return 微信公众号配置
     */
    @Override
    public List<WxMpConfig> selectWxMpConfigList(WxMpConfig wxMpConfig)
    {
        return wxMpConfigMapper.selectWxMpConfigList(wxMpConfig);
    }

    /**
     * 新增微信公众号配置
     * 
     * @param wxMpConfig 微信公众号配置
     * @return 结果
     */
    @Override
    public int insertWxMpConfig(WxMpConfig wxMpConfig)
    {
        wxMpConfig.setCreateTime(DateUtils.getNowDate());
        return wxMpConfigMapper.insertWxMpConfig(wxMpConfig);
    }

    /**
     * 修改微信公众号配置
     * 
     * @param wxMpConfig 微信公众号配置
     * @return 结果
     */
    @Override
    public int updateWxMpConfig(WxMpConfig wxMpConfig)
    {
        wxMpConfig.setUpdateTime(DateUtils.getNowDate());
        return wxMpConfigMapper.updateWxMpConfig(wxMpConfig);
    }

    /**
     * 批量删除微信公众号配置
     * 
     * @param ids 需要删除的微信公众号配置主键
     * @return 结果
     */
    @Override
    public int deleteWxMpConfigByIds(Long[] ids)
    {
        return wxMpConfigMapper.deleteWxMpConfigByIds(ids);
    }

    /**
     * 删除微信公众号配置信息
     * 
     * @param id 微信公众号配置主键
     * @return 结果
     */
    @Override
    public int deleteWxMpConfigById(Long id)
    {
        return wxMpConfigMapper.deleteWxMpConfigById(id);
    }

    @Override
    public WxMpConfig getOne() {
        WxMpConfig wxMpConfig = new WxMpConfig();
        return   wxMpConfigMapper.selectWxMpConfigList(wxMpConfig).get(0);
    }
}
