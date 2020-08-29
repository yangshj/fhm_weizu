package com.weizu.pojo.chatroom;

public class CreateChatRoomRE {

    /** success/fail */
    private String result;
    /**  用户id  */
    private Long roomId;
    /**  消息  */
    private String msg;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public Long getRoomId() {
        return roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
