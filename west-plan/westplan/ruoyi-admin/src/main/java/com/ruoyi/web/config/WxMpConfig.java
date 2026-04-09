package com.ruoyi.web.config;

import com.ruoyi.system.service.IWxMpConfigService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 微信公众号配置类
 *
 * @author 西部计划校友平台
 * @date 2026-02-01
 */
@Configuration
public class WxMpConfig {
    @Resource
    private IWxMpConfigService wxMpConfigService;

    /**
     * 微信公众号配置存储
     */
    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        // 从数据库加载微信配置（系统管理模块维护）
        com.ruoyi.system.domain.WxMpConfig dbConfig = wxMpConfigService.getOne();
        config.setAppId(dbConfig.getAppId());
        config.setSecret(dbConfig.getAppSecret());
        config.setToken(dbConfig.getToken());
        config.setAesKey(dbConfig.getAesKey());
        return config;
    }

    /**
     * 微信公众号核心服务
     */
    @Bean
    public WxMpService wxMpService() {
        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(wxMpConfigStorage());
        return service;
    }
}