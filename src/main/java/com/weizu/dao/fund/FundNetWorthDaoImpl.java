package com.weizu.dao.fund;

import com.weizu.pojo.fund.FundNetWorthBean;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.fh.dao.DaoSupport;
import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;


@Repository("fundNetWorthDaoImpl")
public class FundNetWorthDaoImpl extends DaoSupport implements FundNetWorthDao {

    @Override
    public Integer insertFundNetWorth(FundNetWorthBean bean) throws  Exception{
        return (Integer) this.save("com.weizu.fund.insertFundNetWorth", bean);
    }

    @Override
    public Integer updateFundNetWorth(FundNetWorthBean bean) throws Exception {
        return (Integer) this.update("com.weizu.fund.updateFundNetWorth", bean);
    }

    @Override
    public void deleteFundNetWorth(FundNetWorthBean bean) throws Exception {
        this.delete("com.weizu.fund.deleteFundNetWorth", bean);
    }

    @Override
    public List<FundNetWorthBean> findAllFundNetWorth(FundNetWorthBean bean) throws Exception {
        return (List<FundNetWorthBean>) this.findForList("com.weizu.fund.findAllFundNetWorth", bean);
    }
}
