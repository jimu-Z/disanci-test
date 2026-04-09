package com.ruoyi.system.domain.dto;

import java.util.Map;

public class CountInfo {
    //学生录取数
    private Integer studentAdmissionCount;
    //学生报名数
    private Integer studentApplyCount;
    //服务地区数
    private Integer toArea;
    //帖子数量
    private Integer postCount;
    private Map<String,Integer> areasInfo;
    //每年录取人数
    private Map<String,Integer> studentAdmissionInfo;

}
