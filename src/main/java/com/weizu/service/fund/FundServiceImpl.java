package com.weizu.service.fund;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.fund.FundBean;
import com.weizu.dao.fund.FundDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FundServiceImpl")
public class FundServiceImpl implements FundService {
    @Autowired
    private FundDao fundDao;
    @Override
    public FundBean findFundById(FundBean bean) throws Exception{
        return fundDao.findFundById(bean);
    }
    public Integer inserFund(FundBean bean) throws  Exception{
        return  fundDao.inserFund(bean);
    }
    public Integer updateFund(FundBean bean) throws  Exception{
        return  fundDao.updateFund(bean);
    }
    public void deleteFund(FundBean bean) throws  Exception{
        fundDao.deleteFund(bean);
    }
    public List<FundBean> findFundByCondition(FundBean bean) throws Exception{
        return  fundDao.findFundByCondition(bean);
    }
    @Override
    public List<PageData> getAllFundListPage(Page page) throws Exception {
        return fundDao.getAllFundListPage(page);
    }
}
