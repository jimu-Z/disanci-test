package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.FriendApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendApplyMapper {
    /**
     * 添加好友申请
     * @param apply
     * @return
     */
    int insertApply(FriendApply apply);
    /**
     * 根据id查询申请信息
     * @param id
     * @return
     */
    FriendApply selectById(@Param("id") Long id);
    /**
     * 修改申请状态
     * @param id
     * @param status
     * @return
     */
    int updateApplyStatus(@Param("id") Long id, @Param("status") Integer status);
    /**
     * 检查是否存在申请
     * @param applyUserId
     * @param targetUserId
     * @return
     */
    FriendApply checkExistApply(@Param("applyUserId") Long applyUserId, @Param("targetUserId") Long targetUserId);

    /**
     * 查询我收到的好友申请（关联申请人信息）
     * @param targetUserId
     * @return
     */
    List<FriendApply> selectMyReceiveApply(@Param("targetUserId") Long targetUserId);

    /**
     * 查询我发出的好友申请（关联目标人信息）
     * @param applyUserId
     * @return
     */
    List<FriendApply> selectMySendApply(@Param("applyUserId") Long applyUserId);
}