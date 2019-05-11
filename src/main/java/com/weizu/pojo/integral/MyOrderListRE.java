package com.weizu.pojo.integral;

import com.weizu.pojo.oa.BaseRE;

import java.util.List;

/**
 * 我的订单列表
 */
public class MyOrderListRE extends BaseRE {

    private List<OrderInfoBean> list;

    public List<OrderInfoBean> getList() {
        return list;
    }
    public void setList(List<OrderInfoBean> list) {
        this.list = list;
    }
}
