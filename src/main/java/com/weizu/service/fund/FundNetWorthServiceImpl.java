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
    public Integer insertFundNetWorth(FundNetWorthBean bean) throws  Exception{
        return  fundNetWorthDao.insertFundNetWorth(bean);
    }

    @Override
    public Integer updateFundNetWorth(FundNetWorthBean bean) throws  Exception{
        return  fundNetWorthDao.updateFundNetWorth(bean);
    }

    @Override
    public void deleteFundNetWorth(Long id) throws  Exception{
        fundNetWorthDao.deleteFundNetWorth(id);
    }

    @Override
    public void deleteFundNetWorthByFundId(Long id) throws Exception {
        fundNetWorthDao.deleteFundNetWorthByFundId(id);
    }


    @Override
    public List<FundNetWorthBean> findAllFundNetWorthByCondition(FundNetWorthBean bean) throws Exception{
        return  fundNetWorthDao.findAllFundNetWorthByCondition(bean);
    }
}
