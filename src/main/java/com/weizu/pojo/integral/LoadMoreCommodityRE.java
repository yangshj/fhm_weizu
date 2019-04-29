package com.weizu.pojo.integral;

import com.weizu.pojo.oa.BaseRE;

import java.util.List;

public class LoadMoreCommodityRE extends BaseRE {

    private List<CommodityBean> list;

    public List<CommodityBean> getList() {
        return list;
    }
    public void setList(List<CommodityBean> list) {
        this.list = list;
    }
}
