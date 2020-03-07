package com.weizu.dao.fund;

import com.weizu.pojo.fund.FundNetWorthBean;

import java.util.List;

public interface FundNetWorthDao {
    List<FundNetWorthBean> findAllFundNetWorth(FundNetWorthBean bean) throws Exception;
    Integer inserFundNetWorth(FundNetWorthBean bean) throws Exception;
    Integer updateFundNetWorth(FundNetWorthBean bean) throws Exception;
    void deleteFundNetWorth(FundNetWorthBean bean) throws Exception;
}
