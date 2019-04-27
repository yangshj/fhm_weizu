package com.weizu.service.integral;

import com.weizu.dao.integral.IntegralDao;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.pojo.integral.IntegralBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("integralServiceImpl")
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    private IntegralDao integralDao;

    @Override
    public Integer insertIntegral(IntegralBean bean) throws Exception {
        return integralDao.insertIntegral(bean);
    }

    @Override
    public Integer updateIntegral(IntegralBean bean) throws Exception {
        return integralDao.updateIntegral(bean);
    }

    @Override
    public void deleteIntegral(IntegralBean bean) throws Exception {
        integralDao.deleteIntegral(bean);
    }

    @Override
    public List<IntegralBean> getIntegralByUserId(Long userId) throws Exception {
        return integralDao.getIntegralByUserId(userId);
    }

    @Override
    public List<IntegralBean> getIntegralByCondition(IntegralBean bean) throws Exception {
        return integralDao.getIntegralByCondition(bean);
    }

    @Override
    public List<IntegralBean> getAllIntegral(IntegralBean bean) throws Exception {
        return integralDao.getAllIntegral(bean);
    }
}
