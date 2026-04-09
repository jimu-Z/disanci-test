package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.UserMessage;

import java.util.List;

public interface UserMessageMapper {
    /**
     * 新增用户留言
     * @param userMessage
     * @return
     */
    int insert(UserMessage userMessage);
    /**
     * 查询用户留言
     * @param userId
     * @return
     */
    List<UserMessage> selectMyMessage(Long userId);
    /**
     * 根据id查询用户留言
     * @param id
     * @return
     */
    UserMessage getUserMessageById(Long id);
    /**
     * 删除用户留言
     * @param id
     * @return
     */
    int deleteById(Long id);
    /**
     * 回复用户留言
     * @param userMessage
     * @return
     */
    int replyMessage(UserMessage userMessage);
    /**
     * 查询该用户所有留言
     * @param userMessage
     * @return
     */
    List<UserMessage> selectUserAll(UserMessage userMessage);
    /**
     * 查询该用户所有回复的留言
     * @param userMessage
     * @return
     */
    List<UserMessage> selectRePlyUserAll(UserMessage userMessage);
}
