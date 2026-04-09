package com.ruoyi.system.mapper;
import com.ruoyi.system.domain.Message;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface MessageMapper {
    // 查询所有（管理员）
    List<Message> selectAll(Message message);
    // 查询我的留言（用户）
    List<Message> selectMyMessage(@Param("userId") Long userId);
    // 新增
    int insert(Message message);
    // 管理员回复
    int replyMessage(Message message);
    // 删除
    int deleteById(@Param("id") Long id);

    Message getMessageById(@Param("id")Long id);
}