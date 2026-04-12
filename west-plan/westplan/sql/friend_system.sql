-- 好友系统相关表

-- 1. 好友申请表
CREATE TABLE `friend_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `apply_user_id` bigint(20) NOT NULL COMMENT '申请人ID（关联student_admission.id）',
  `target_user_id` bigint(20) NOT NULL COMMENT '被申请人ID（关联student_admission.id）',
  `apply_msg` varchar(255) DEFAULT NULL COMMENT '申请消息',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态（1待处理 2同意 3拒绝 4撤回）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `idx_apply_user` (`apply_user_id`),
  KEY `idx_target_user` (`target_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友申请表';

-- 2. 好友关系表
CREATE TABLE `friend_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID（关联student_admission.id）',
  `friend_id` bigint(20) NOT NULL COMMENT '好友ID（关联student_admission.id）',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态（1正式好友 2已屏蔽/删除）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_friend` (`user_id`,`friend_id`),
  KEY `idx_friend_id` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友关系表';
