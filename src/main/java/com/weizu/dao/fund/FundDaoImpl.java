package com.weizu.dao.fund;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.fund.FundBean;
import org.springframework.stereotype.Repository;
import com.fh.dao.DaoSupport;

@Repository("FundDaoImpl")
public class FundDaoImpl extends DaoSupport implements FundDao {
    @Override
    public FundBean findFundById(FundBean bean) throws Exception{
        return (FundBean) this.findForObject("com.weizu.fund.findFundById", bean);
    }
    @Override
    public Integer inserFund(FundBean bean) throws  Exception{
        return (Integer) this.save("com.weizu.fund.inserFund", bean);
    }
    @Override
    public Integer updateFund(FundBean bean) throws Exception {
        return (Integer) this.update("com.weizu.fund.updateFund", bean);
    }

    @Override
    public void deleteFund(FundBean bean) throws Exception {
        this.delete("com.weizu.fund.deleteFund", bean);
    }
    @Override
    public List<FundBean> findFundByCondition(FundBean bean)
            throws Exception {
        return (List<FundBean>) this.findForList("com.weizu.fund.findFundByCondition", bean);
    }
    @Override
    public List<PageData> getAllFundListPage(Page page) throws Exception {
        return (List<PageData>) this.findForList("com.weizu.fund.getAllFundListPage", page);
    }
}
