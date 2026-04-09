package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信公众号配置对象 wx_mp_config
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class WxMpConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 公众号APPID */
    @Excel(name = "公众号APPID")
    private String appId;

    /** 公众号APPSECRET */
    @Excel(name = "公众号APPSECRET")
    private String appSecret;

    /** 公众号Token */
    @Excel(name = "公众号Token")
    private String token;

    /** 消息加密密钥 */
    @Excel(name = "消息加密密钥")
    private String aesKey;

    /** 登录回调地址 */
    @Excel(name = "登录回调地址")
    private String loginRedirectUrl;

    /** 状态（0禁用1启用） */
    @Excel(name = "状态", readConverterExp = "0=禁用1启用")
    private Integer status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setAppId(String appId) 
    {
        this.appId = appId;
    }

    public String getAppId() 
    {
        return appId;
    }

    public void setAppSecret(String appSecret) 
    {
        this.appSecret = appSecret;
    }

    public String getAppSecret() 
    {
        return appSecret;
    }

    public void setToken(String token) 
    {
        this.token = token;
    }

    public String getToken() 
    {
        return token;
    }

    public void setAesKey(String aesKey) 
    {
        this.aesKey = aesKey;
    }

    public String getAesKey() 
    {
        return aesKey;
    }

    public void setLoginRedirectUrl(String loginRedirectUrl) 
    {
        this.loginRedirectUrl = loginRedirectUrl;
    }

    public String getLoginRedirectUrl() 
    {
        return loginRedirectUrl;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("appId", getAppId())
            .append("appSecret", getAppSecret())
            .append("token", getToken())
            .append("aesKey", getAesKey())
            .append("loginRedirectUrl", getLoginRedirectUrl())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
