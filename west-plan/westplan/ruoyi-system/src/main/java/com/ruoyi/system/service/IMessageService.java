package com.ruoyi.system.service;

import com.ruoyi.system.domain.Message;

import java.util.List;

public interface IMessageService {
    List<Message> getAll(Message message);                  // 管理员查全部
    List<Message> getMy(Long UserId);    // 用户查自己
    boolean add(Message message);            // 新增
    boolean reply(Message message);          // 回复
    boolean delete(Long id);

    Message getMessageById(Long id);
}
