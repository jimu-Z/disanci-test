package com.ruoyi.system.service.impl;
import com.ruoyi.system.domain.Message;
import com.ruoyi.system.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import com.ruoyi.system.service.IMessageService;
@Service
public class MessageServiceImpl implements IMessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public List<Message> getAll(Message message) {

        return messageMapper.selectAll( message);
    }

    @Override
    public List<Message> getMy(Long userId) {
        return messageMapper.selectMyMessage(userId);
    }

    @Override
    public boolean add(Message message) {
        return messageMapper.insert(message) > 0;
    }

    @Override
    public boolean reply(Message message) {
        return messageMapper.replyMessage(message) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return messageMapper.deleteById(id) > 0;
    }

    @Override
    public Message getMessageById(Long id) {
        return messageMapper.getMessageById(id);
    }
}
