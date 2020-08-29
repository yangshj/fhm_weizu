package com.weizu.pojo.chatroom;


import java.util.List;

public class GetAllChatRoomRE {

    /** success/fail */
    private String result;
    /**  用户位置信息列表  */
    private List<ChatRoomBean> listData;
    /**  消息  */
    private String msg;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public List<ChatRoomBean> getListData() {
        return listData;
    }
    public void setListData(List<ChatRoomBean> listData) {
        this.listData = listData;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
