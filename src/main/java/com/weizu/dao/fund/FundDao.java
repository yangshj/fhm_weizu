package com.weizu.dao.fund;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.fund.FundBean;

public interface FundDao {

    /**查询基金信息*/
    FundBean findFundById(FundBean bean) throws Exception;

    /**查询基金信息*/
    List<FundBean> findFundByCondition(FundBean bean) throws Exception;

    /**新增基金信息*/
    Integer insertFund(FundBean bean) throws Exception;

    /**修改基金信息*/
    Integer updateFund(FundBean bean) throws Exception;

    /**删除基金信息*/
    void deleteFund(FundBean bean) throws Exception;

    /**获取所有基金信息返回到前台页面列表*/
    List<PageData> getAllFundListPage(Page page) throws Exception;

    /**批量删除基金信息*/
    void deleteAllU(String[] USER_IDS) throws Exception;
}
