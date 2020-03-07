package com.weizu.service.fund;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.fund.FundBean;

import java.util.List;

public interface FundService {
    FundBean findFundById(FundBean bean) throws Exception;
    List<FundBean> findFundByCondition(FundBean bean) throws Exception;
    Integer inserFund(FundBean bean) throws  Exception;
    Integer updateFund(FundBean bean) throws  Exception;
    void deleteFund(FundBean bean) throws  Exception;
    List<PageData> getAllFundListPage(Page page) throws Exception;
    /*
     * 批量删除用户
     */
     void deleteAllU(String[] USER_IDS)throws Exception;
}
