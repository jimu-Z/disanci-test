package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.StudentAdmission;
import com.ruoyi.system.mapper.FriendRelationMapper;
import com.ruoyi.system.service.IFriendRelationService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
public class FriendApplyRelationImpl implements IFriendRelationService {
    @Resource
    private FriendRelationMapper friendRelationMapper;

    @Override
    public int updateApplyStatus(Long userId, Long targetId) {
        return friendRelationMapper.updateFriendStatus(userId,targetId);
    }

    @Override
    public List<StudentAdmission> selectMyRealFriend(Long userId) {
        return friendRelationMapper.selectMyRealFriend(userId);
    }

    @Override
    public StudentAdmission selectByIdStudentAdmission(Long userId, Long targetId) {
        return friendRelationMapper.selectByIdStudentAdmission(userId,targetId);
    }
}

