package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.FriendApply;
import com.ruoyi.system.mapper.FriendApplyMapper;
import com.ruoyi.system.mapper.FriendRelationMapper;
import com.ruoyi.system.service.IFriendApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendApplyServiceImpl implements IFriendApplyService {
    @Resource
    private FriendApplyMapper friendApplyMapper;
    @Resource
    private FriendRelationMapper friendRelationMapper;

    @Override
    public int insertFriendApply(FriendApply friendApply) {
        Long currentUserId = friendApply.getApplyUserId();
        friendApply.setApplyUserId(currentUserId);

        // 1. 校验：不能加自己
        if (currentUserId.equals(friendApply.getTargetUserId())) {
            return 0 ;
        }

        // 4. 执行插入
        friendApply.setStatus(1); // 1-待处理
        return friendApplyMapper.insertApply(friendApply);
    }

    @Override
    public FriendApply selectFriendApplyById(Long id) {
        return friendApplyMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateApplyStatus(FriendApply friendApply) {
        if (friendApply.getStatus() == null){
            return 0;
        }
        if (friendApply.getStatus() == 2){
            //如果双方都不是好友，则添加双向好友
            if (friendRelationMapper.countIsFriend(friendApply.getApplyUserId(), friendApply.getTargetUserId()) == 0 && friendRelationMapper.countIsFriend(friendApply.getTargetUserId(), friendApply.getApplyUserId()) == 0){
                friendRelationMapper.insertOneRelation(friendApply.getApplyUserId(), friendApply.getTargetUserId());
                friendRelationMapper.insertOneRelation(friendApply.getTargetUserId(), friendApply.getApplyUserId());
            }//如果A是B的好友，则添加B为A的好友
            else if (friendRelationMapper.countIsFriend(friendApply.getApplyUserId(), friendApply.getTargetUserId()) == 0 && friendRelationMapper.countIsFriend(friendApply.getTargetUserId(), friendApply.getApplyUserId()) > 0){
                friendRelationMapper.insertOneRelation(friendApply.getApplyUserId(), friendApply.getTargetUserId());
            }
            //如果B是A的好友，则添加A为B的好友
            else if (friendRelationMapper.countIsFriend(friendApply.getApplyUserId(), friendApply.getTargetUserId()) > 0 && friendRelationMapper.countIsFriend(friendApply.getTargetUserId(), friendApply.getApplyUserId()) == 0){
                friendRelationMapper.insertOneRelation(friendApply.getTargetUserId(), friendApply.getApplyUserId());
            }

        }
        return friendApplyMapper.updateApplyStatus(friendApply.getId(), friendApply.getStatus());
    }

    @Override
    public List<FriendApply> getMyReceiveApply(Long userId) {
        return friendApplyMapper.selectMyReceiveApply(userId);
    }

    @Override
    public List<FriendApply> getMySendApply(Long userId) {
        return friendApplyMapper.selectMySendApply(userId);
    }
}

