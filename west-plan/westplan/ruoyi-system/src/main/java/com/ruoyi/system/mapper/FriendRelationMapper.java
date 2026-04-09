package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.StudentAdmission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendRelationMapper {
    int insertOneRelation(@Param("userId") Long userId, @Param("friendId") Long friendId);
    int countIsFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);
    List<StudentAdmission> selectMyRealFriend(@Param("userId") Long userId);
    int updateFriendStatus(@Param("userId") Long userId, @Param("friendId") Long friendId);
    StudentAdmission selectByIdStudentAdmission(@Param("userId") Long userId, @Param("friendId") Long friendId);

}
