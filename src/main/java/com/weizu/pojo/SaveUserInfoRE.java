package com.weizu.pojo;

import java.util.List;
/**
 * 保存用户返回值
 */
public class SaveUserInfoRE {

    /** success/fail */
    private String result;
    /**  用户id  */
    private Long addressLookId;
    /**  消息  */
    private String msg;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public Long getAddressLookId() {
        return addressLookId;
    }
    public void setAddressLookId(Long addressLookId) {
        this.addressLookId = addressLookId;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
