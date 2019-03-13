package com.weizu.pojo.oa;

import java.util.List;

public class GetAllShiftsRE extends BaseRE {

    /**  团队信息列表  */
    private List<SignShiftBean> listData;


    public List<SignShiftBean> getListData() {
        return listData;
    }
    public void setListData(List<SignShiftBean> listData) {
        this.listData = listData;
    }
}
