package com.weizu.pojo.integral;

import com.weizu.pojo.oa.BaseRE;

/**
 * 订单详情
 */
public class OrderDetailRE extends BaseRE {

    private OrderInfoBean orderInfo;


    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }
    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }
}
