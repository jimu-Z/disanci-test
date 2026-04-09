package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户绑定对象 wp_wx_user
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class WpWxUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 关联sys_user.id */
    @Excel(name = "关联sys_user.id")
    private Long userId;

    /** 微信OpenID */
    @Excel(name = "微信OpenID")
    private String wxOpenid;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String wxNickname;

    /** 微信头像 */
    @Excel(name = "微信头像")
    private String wxAvatar;

    /** 微信性别（0-未知，1-男，2-女） */
    @Excel(name = "微信性别", readConverterExp = "0=-未知，1-男，2-女")
    private Integer wxGender;

    /** 绑定状态（0-未绑定，1-已绑定） */
    @Excel(name = "绑定状态", readConverterExp = "0=-未绑定，1-已绑定")
    private Integer bindStatus;

    /** 删除标志（0-正常，1-删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setWxOpenid(String wxOpenid) 
    {
        this.wxOpenid = wxOpenid;
    }

    public String getWxOpenid() 
    {
        return wxOpenid;
    }

    public void setWxNickname(String wxNickname) 
    {
        this.wxNickname = wxNickname;
    }

    public String getWxNickname() 
    {
        return wxNickname;
    }

    public void setWxAvatar(String wxAvatar) 
    {
        this.wxAvatar = wxAvatar;
    }

    public String getWxAvatar() 
    {
        return wxAvatar;
    }

    public void setWxGender(Integer wxGender) 
    {
        this.wxGender = wxGender;
    }

    public Integer getWxGender() 
    {
        return wxGender;
    }

    public void setBindStatus(Integer bindStatus) 
    {
        this.bindStatus = bindStatus;
    }

    public Integer getBindStatus() 
    {
        return bindStatus;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("wxOpenid", getWxOpenid())
            .append("wxNickname", getWxNickname())
            .append("wxAvatar", getWxAvatar())
            .append("wxGender", getWxGender())
            .append("bindStatus", getBindStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
