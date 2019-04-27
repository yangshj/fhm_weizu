package com.weizu.dao.integral;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.integral.IntegralRecordBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("integralDaoImpl")
public class IntegralRecordDaoImpl extends DaoSupport implements IntegralRecordDao {

    @Override
    public Integer insertIntegralRecord(IntegralRecordBean bean) throws Exception {
        return (Integer) this.save("com.weizu.integral.insertIntegralRecord", bean);
    }

    @Override
    public Integer updateIntegralRecord(IntegralRecordBean bean) throws Exception {
        return (Integer) this.update("com.weizu.integral.updateIntegralRecord",bean);
    }

    @Override
    public void deleteIntegralRecord(IntegralRecordBean bean) throws Exception {
        this.delete("com.weizu.integral.deleteIntegralRecord",bean);
    }

    @Override
    public List<IntegralRecordBean> getIntegralRecordByIntegralId(Long integralId) throws Exception {
        return (List<IntegralRecordBean>) this.findForList("com.weizu.integral.getIntegralRecordByIntegralId", integralId);
    }


}
