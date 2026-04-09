package com.ruoyi.system.domain;

import java.time.LocalDateTime;

/**
 * 留言对象 user_message
 *
 * @author ruoyi
 */
public class UserMessage {
    private Long id;
    private Long userId;            // 发送人标识（用户ID）
    private Long receiveUserId;     // 接收人ID
    private String nickname;        // 发送者昵称
    private String receiveNickname; // 接收人昵称（新添加）
    private String content;         // 内容
    private String contact;         // 联系方式
    private String replyContent;    // 回复内容
    private LocalDateTime replyTime;// 回复时间
    private Integer status;         // 0未回复 1已回复
    private LocalDateTime createTime;
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // --- 新补充的 Getter ---
    public String getReceiveNickname() {
        return receiveNickname;
    }

    // --- 新补充的 Setter ---
    public void setReceiveNickname(String receiveNickname) {
        this.receiveNickname = receiveNickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    // 更新后的 toString 方法
    @Override
    public String toString() {
        return "UserMessage{" +
                "id=" + id +
                ", userId=" + userId +
                ", receiveUserId=" + receiveUserId +
                ", nickname='" + nickname + '\'' +
                ", receiveNickname='" + receiveNickname + '\'' + // 已加入
                ", content='" + content + '\'' +
                ", contact='" + contact + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", replyTime=" + replyTime +
                ", status=" + status +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}