package com.ruoyi.system.enums;

public enum AuditStatusEnum {
    PENDING(0, "待审核"),
    PASS(1, "审核通过"),
    REJECT(2, "审核驳回");

    private final int code;
    private final String desc;

    AuditStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}