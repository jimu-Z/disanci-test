package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校友视频对象 alumni_video
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public class AlumniVideo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 校友用户ID（关联sys_user.id） */
    @Excel(name = "校友用户ID", readConverterExp = "关=联sys_user.id")
    private Long userId;

    /** 视频名称 */
    @Excel(name = "视频名称")
    private String videoName;

    /** 视频OSS地址 */
    @Excel(name = "视频OSS地址")
    private String videoUrl;

    /** 视频封面OSS地址 */
    @Excel(name = "视频封面OSS地址")
    private String coverUrl;

    /** 视频描述 */
    @Excel(name = "视频描述")
    private String videoDesc;

    /** 播放量 */
    @Excel(name = "播放量")
    private Long playCount;

    /** 是否公开（0私密1公开） */
    @Excel(name = "是否公开", readConverterExp = "0=私密1公开")
    private Integer isPublic;

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

    public void setVideoName(String videoName) 
    {
        this.videoName = videoName;
    }

    public String getVideoName() 
    {
        return videoName;
    }

    public void setVideoUrl(String videoUrl) 
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() 
    {
        return videoUrl;
    }

    public void setCoverUrl(String coverUrl) 
    {
        this.coverUrl = coverUrl;
    }

    public String getCoverUrl() 
    {
        return coverUrl;
    }

    public void setVideoDesc(String videoDesc) 
    {
        this.videoDesc = videoDesc;
    }

    public String getVideoDesc() 
    {
        return videoDesc;
    }

    public void setPlayCount(Long playCount) 
    {
        this.playCount = playCount;
    }

    public Long getPlayCount() 
    {
        return playCount;
    }

    public void setIsPublic(Integer isPublic) 
    {
        this.isPublic = isPublic;
    }

    public Integer getIsPublic() 
    {
        return isPublic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("videoName", getVideoName())
            .append("videoUrl", getVideoUrl())
            .append("coverUrl", getCoverUrl())
            .append("videoDesc", getVideoDesc())
            .append("playCount", getPlayCount())
            .append("isPublic", getIsPublic())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
