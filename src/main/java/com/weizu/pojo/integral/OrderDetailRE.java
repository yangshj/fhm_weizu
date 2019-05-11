package com.weizu.pojo.integral;

import com.weizu.pojo.oa.BaseRE;

/**
 * 订单详情
 */
public class OrderDetailRE extends BaseRE {

    /** 订单详情 */
    private OrderBean orderInfo;
    /** 商品详情 */
    private CommodityBean commodityInfo;


    public OrderBean getOrderInfo() {
        return orderInfo;
    }
    public void setOrderInfo(OrderBean orderInfo) {
        this.orderInfo = orderInfo;
    }
    public CommodityBean getCommodityInfo() {
        return commodityInfo;
    }
    public void setCommodityInfo(CommodityBean commodityInfo) {
        this.commodityInfo = commodityInfo;
    }
}
