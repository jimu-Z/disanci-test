package com.ruoyi.system.domain;

import lombok.Data;

import java.util.Date;
@Data
public class FriendRelation {


        private Long id;
        private Long userId;
        private Long friendId;
        private Integer status;
        private Date createTime;

}
