package com.weizu.dao.integral;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.integral.IntegralBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("integralDaoImpl")
public class IntegralDaoImpl extends DaoSupport implements IntegralDao{

    @Override
    public Integer insertIntegral(IntegralBean bean) throws Exception {
        return (Integer) this.save("com.weizu.integral.insertIntegral", bean);
    }

    @Override
    public Integer updateIntegral(IntegralBean bean) throws Exception {
        return (Integer) this.update("com.weizu.integral.updateIntegral",bean);
    }

    @Override
    public void deleteIntegral(IntegralBean bean) throws Exception {
        this.delete("com.weizu.integral.deleteIntegral",bean);
    }

    @Override
    public List<IntegralBean> getIntegralByUserId(Long userId) throws Exception {
        return (List<IntegralBean>) this.findForList("com.weizu.integral.getIntegralByUserId", userId);
    }

    @Override
    public List<IntegralBean> getIntegralByCondition(IntegralBean bean) throws Exception {
        return (List<IntegralBean>) this.findForList("com.weizu.integral.getIntegralByCondition", bean);
    }

    @Override
    public List<IntegralBean> getAllIntegral(IntegralBean bean) throws Exception {
        return (List<IntegralBean>) this.findForList("com.weizu.integral.getAllIntegral", bean);
    }
}
