package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.oa.SignRecordBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("signRecordDaoImpl")
public class SignRecordDaoImpl extends DaoSupport implements SignRecordDao {

    @Override
    public SignRecordBean findSignRecordById(SignRecordBean bean) throws Exception {
        return (SignRecordBean) this.findForObject("com.weizu.oa.signRecord.findSignRecordById", bean);
    }

    @Override
    public List<SignRecordBean> findSignRecordByCondition(SignRecordBean bean) throws Exception {
        return (List<SignRecordBean>) this.findForList("com.weizu.oa.signRecord.findSignRecordByCondition", bean);
    }

    @Override
    public Integer insertSignRecord(SignRecordBean bean) throws Exception {
        return (Integer) this.save("com.weizu.oa.signRecord.insertSignRecord", bean);
    }

    @Override
    public Integer updateSignRecord(SignRecordBean bean) throws Exception {
        return (Integer) this.update("com.weizu.oa.signRecord.updateSignRecord", bean);
    }

    @Override
    public void deleteSignRecord(SignRecordBean bean) throws Exception {
        this.delete("com.weizu.oa.signRecord.deleteSignRecord", bean);
    }
}
