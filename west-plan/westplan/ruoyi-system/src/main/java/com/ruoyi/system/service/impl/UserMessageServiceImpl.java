package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Message;
import com.ruoyi.system.domain.UserMessage;
import com.ruoyi.system.mapper.MessageMapper;
import com.ruoyi.system.mapper.UserMessageMapper;
import com.ruoyi.system.service.IUserMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserMessageServiceImpl implements IUserMessageService {
    @Resource
    private UserMessageMapper userMessageMapper;
    @Override
    public boolean add(UserMessage userMessage) {
        return userMessageMapper.insert(userMessage) > 0;
    }

    @Override
    public List<UserMessage> getMy(Long userId) {
        return userMessageMapper.selectMyMessage(userId);
    }

    @Override
    public UserMessage getUserMessageById(Long id) {
        return  userMessageMapper.getUserMessageById(id);
    }

    @Override
    public boolean delete(Long id) {
        return userMessageMapper.deleteById(id) > 0;
    }

    @Override
    public boolean reply(UserMessage userMessage) {
        return userMessageMapper.replyMessage(userMessage) > 0;
    }

    @Override
    public List<UserMessage> getAll(UserMessage userMessage) {
        return userMessageMapper.selectUserAll(userMessage);
    }

    @Override
    public List<UserMessage> getRePlyAll(UserMessage userMessage) {
        return userMessageMapper.selectRePlyUserAll(userMessage);
    }
}
