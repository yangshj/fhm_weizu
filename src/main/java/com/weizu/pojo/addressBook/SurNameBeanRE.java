package com.weizu.pojo.addressBook;

import com.weizu.pojo.oa.BaseRE;

import java.util.List;

public class SurNameBeanRE extends BaseRE{

    /** success/fail */
    private String result;
    /**  用户位置信息列表  */
    private List<SurNameBean> listData;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public List<SurNameBean> getListData() {
        return listData;
    }
    public void setListData(List<SurNameBean> listData) {
        this.listData = listData;
    }
}
