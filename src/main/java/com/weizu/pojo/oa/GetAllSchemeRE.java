package com.weizu.pojo.oa;

import java.util.List;

public class GetAllSchemeRE extends BaseRE {

    /**  团队信息列表  */
    private List<SignSchemeBean> listData;


    public List<SignSchemeBean> getListData() {
        return listData;
    }
    public void setListData(List<SignSchemeBean> listData) {
        this.listData = listData;
    }
}
