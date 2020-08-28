package com.weizu.pojo.chatroom;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天室-人 关系表
 * @version 1.0
 * @date 2020-08-28
 */
public class ChatRoomMemberBean implements Serializable {
    private Long id;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * openId
     */
    private String openId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 昵称
     */
    private String nickName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}