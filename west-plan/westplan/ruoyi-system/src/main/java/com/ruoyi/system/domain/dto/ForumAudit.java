package com.ruoyi.system.domain.dto;

import lombok.Data;

@Data
public class ForumAudit {
    private Long id;
    private String auditRemark;
    private Long[]   ids;
    private Integer auditStatus;
}
