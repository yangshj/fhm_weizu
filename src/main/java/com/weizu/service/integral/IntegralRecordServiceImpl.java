package com.weizu.service.integral;

import com.weizu.dao.integral.IntegralRecordDao;
import com.weizu.pojo.integral.IntegralRecordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("integralRecordServiceImpl")
public class IntegralRecordServiceImpl implements IntegralRecordService {

    @Autowired
    private IntegralRecordDao integralRecordDao;

    @Override
    public Integer insertIntegralRecord(IntegralRecordBean bean) throws Exception {
        return integralRecordDao.insertIntegralRecord(bean);
    }

    @Override
    public Integer updateIntegralRecord(IntegralRecordBean bean) throws Exception {
        return integralRecordDao.updateIntegralRecord(bean);
    }

    @Override
    public void deleteIntegralRecord(IntegralRecordBean bean) throws Exception {
        integralRecordDao.deleteIntegralRecord(bean);
    }

    @Override
    public List<IntegralRecordBean> getIntegralRecordByIntegralId(Long integralId) throws Exception {
        return integralRecordDao.getIntegralRecordByIntegralId(integralId);
    }
}
