package com.ruoyi.system.service;

import com.ruoyi.system.domain.FriendApply;

import java.util.List;

public interface IFriendApplyService {
    /**
     * 添加好友申请
     *
     * @param friendApply
     * @return
     */
    int insertFriendApply(FriendApply friendApply);
    /**
     * 根据id查询申请信息
     *
     * @param id
     * @return
     */
    FriendApply selectFriendApplyById(Long id);
    /**
     * 获取好友申请详细信息
     */
    int updateApplyStatus(FriendApply friendApply);
    /**
     * 获取我收到的申请列表
     */
    List<FriendApply> getMyReceiveApply(Long userId);
    /**
     * 获取我发送的申请列表
     */
    List<FriendApply> getMySendApply(Long userId);
}
