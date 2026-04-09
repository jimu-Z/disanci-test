package com.ruoyi.system.service;

import com.ruoyi.system.domain.Message;
import com.ruoyi.system.domain.UserMessage;

import java.util.List;

public interface IUserMessageService {
    /**
     * 给用户留言
     * @param userMessage
     * @return
     */
    boolean add(UserMessage userMessage);
    /**
     * 用户查自己
     * @param userId
     * @return
     */
    List<UserMessage> getMy(Long userId);
    /**
     * 根据id查询留言
     * @param id
     * @return
     */
    UserMessage getUserMessageById(Long id);
    /**
     * 删除留言
     * @param id
     * @return
     */
    boolean delete(Long id);
    /**
     * 回复留言
     * @param userMessage
     * @return
     */
    boolean reply(UserMessage userMessage);
    /**
     * 用户查询所有自己的留言
     * @param userMessage
     * @return
     */
    List<UserMessage> getAll(UserMessage userMessage);
    /**
     * 用户查询自己所有留言的回复
     * @param userMessage
     * @return
     */
    List<UserMessage> getRePlyAll(UserMessage userMessage);
}
