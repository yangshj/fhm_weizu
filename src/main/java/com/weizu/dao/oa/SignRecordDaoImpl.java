package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.oa.SignRecordBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("signRecordDaoImpl")
public class SignRecordDaoImpl extends DaoSupport implements SignRecordDao {
    @Override
    public SignRecordBean findSignRecordById(SignRecordBean bean) throws Exception {
        return null;
    }

    @Override
    public List<SignRecordBean> findSignRecordByCondition(SignRecordBean bean) throws Exception {
        return null;
    }

    @Override
    public Integer insertSignRecord(SignRecordBean bean) throws Exception {
        return null;
    }

    @Override
    public Integer updateSignRecord(SignRecordBean bean) throws Exception {
        return null;
    }

    @Override
    public void deleteSignRecord(SignRecordBean bean) throws Exception {

    }
}
