package com.weizu.dao.fund;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.fund.FundBean;

public interface FundDao {
    FundBean findFundById(FundBean bean) throws Exception;
    List<FundBean> findFundByCondition(FundBean bean) throws Exception;
    Integer inserFund(FundBean bean) throws Exception;
    Integer updateFund(FundBean bean) throws Exception;
    void deleteFund(FundBean bean) throws Exception;
    List<PageData> getAllFundListPage(Page page) throws Exception;
    void deleteAllU(String[] USER_IDS) throws Exception;
}
