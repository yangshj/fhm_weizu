package com.weizu.service.fund;

import com.weizu.pojo.fund.FundNetWorthBean;

import java.util.List;

public interface FundNetWorthService {
    /**查询所有基金净值信息*/
    List<FundNetWorthBean> findAllFundNetWorth(FundNetWorthBean bean) throws Exception;
    /**新增基金净值信息*/
    Integer insertFundNetWorth(FundNetWorthBean bean) throws  Exception;
    /**修改基金净值信息*/
    Integer updateFundNetWorth(FundNetWorthBean bean) throws  Exception;
    /**删除基金净值信息*/
    void deleteFundNetWorth(FundNetWorthBean bean) throws  Exception;
}
