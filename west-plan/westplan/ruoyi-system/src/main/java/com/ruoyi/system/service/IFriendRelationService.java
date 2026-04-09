package com.ruoyi.system.service;


import com.ruoyi.system.domain.FriendRelation;
import com.ruoyi.system.domain.StudentAdmission;

import java.util.List;

public interface IFriendRelationService {
    /**
     * 修改好友申请状态
     * @param userId
     * @param friendId
     * @return
     */
    int updateApplyStatus(Long userId, Long friendId);

    /**
     * 查看我的所有好友信息
     * @param userId
     * @return
     */
    List<StudentAdmission> selectMyRealFriend(Long userId);

    /**
     * 查询我的好友信息详细信息
     * @param userId
     * @param targetId
     * @return
     */
    StudentAdmission selectByIdStudentAdmission(Long userId, Long targetId);
}
