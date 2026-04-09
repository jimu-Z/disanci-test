package com.ruoyi.system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class FriendApply {
    private Long id;
    private Long applyUserId;
    private Long targetUserId;
    private String applyMsg;
    private Integer status; //1待处理 2同意 3拒绝 4撤回
    private Date createTime;

    //前端展示冗余字段
    private String applyUserName;//申请人姓名
    private String targetUserName;//目标人姓名
    private String applyUserAvatar;//申请人头像
    private String targetUserAvatar;//目标人头像
}