package com.weizu.service.fund;

import com.weizu.pojo.fund.FundNetWorthBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.weizu.dao.fund.FundNetWorthDao;
import java.util.List;


@Service("fundNetWorthServiceImpl")
public class FundNetWorthServiceImpl implements FundNetWorthService {
    @Autowired
    private FundNetWorthDao fundNetWorthDao;

    @Override
    public Integer inserFundNetWorth(FundNetWorthBean bean) throws  Exception{
        return  fundNetWorthDao.inserFundNetWorth(bean);
    }
    @Override
    public Integer updateFundNetWorth(FundNetWorthBean bean) throws  Exception{
        return  fundNetWorthDao.updateFundNetWorth(bean);
    }
    @Override
    public void deleteFundNetWorth(FundNetWorthBean bean) throws  Exception{
        fundNetWorthDao.deleteFundNetWorth(bean);
    }
    @Override
    public List<FundNetWorthBean> findAllFundNetWorth(FundNetWorthBean bean) throws Exception{
        return  fundNetWorthDao.findAllFundNetWorth(bean);
    }
}
