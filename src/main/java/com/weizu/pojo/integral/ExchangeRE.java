package com.weizu.pojo.integral;

import com.weizu.pojo.oa.BaseRE;

/**
 * 兑换商品
 */
public class ExchangeRE extends BaseRE {

    private String orderNo;
    private Long orderId;

    private OrderInfoBean orderInfo;

    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }
    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }
}
