package com.ruoyi.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ForumAudit {
    private Long id;
    private String auditRemark;
    private Long[]   ids;

    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;
}
