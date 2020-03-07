package com.weizu.pojo.fund;

import java.util.List;

/**
 * 基金信息类
 * 对应http接口解析数据
 */
public class FundInfo {


    private FundBean fundBean;
    private List<FundNetWorthBean> items;

    public FundBean getFundBean() {
        return fundBean;
    }
    public void setFundBean(FundBean fundBean) {
        this.fundBean = fundBean;
    }
    public List<FundNetWorthBean> getItems() {
        return items;
    }
    public void setItems(List<FundNetWorthBean> items) {
        this.items = items;
    }
}
