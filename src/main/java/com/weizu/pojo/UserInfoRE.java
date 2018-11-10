package com.weizu.pojo;

import java.util.List;

public class UserInfoRE {

    /** success/fail */
    private String result;
    /**  用户位置信息列表  */
    private List<UserInfoBean> listData;

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
}
