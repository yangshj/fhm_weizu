package com.weizu.pojo.addressBook;

import java.util.List;

public class UserInfoRE {

    /** success/fail */
    private String result;
    /**  用户位置信息列表  */
    private List<UserInfoBean> listData;
    /**  消息  */
    private String msg;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public List<UserInfoBean> getListData() {
        return listData;
    }
    public void setListData(List<UserInfoBean> listData) {
        this.listData = listData;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
