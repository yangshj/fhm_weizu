package com.weizu.service.oa;

import com.weizu.dao.oa.SignRecordDao;
import com.weizu.pojo.oa.SignRecordBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("signRecordServiceImpl")
public class SignRecordServiceImpl implements SignRecordService {

    @Autowired
    private SignRecordDao signRecordDao;

    @Override
    public SignRecordBean findSignRecordById(SignRecordBean bean) throws Exception {
        return signRecordDao.findSignRecordById(bean);
    }

    @Override
    public List<SignRecordBean> findSignRecordByCondition(SignRecordBean bean) throws Exception {
        return signRecordDao.findSignRecordByCondition(bean);
    }

    @Override
    public Integer insertSignRecord(SignRecordBean bean) throws Exception {
        return signRecordDao.insertSignRecord(bean);
    }

    @Override
    public Integer updateSignRecord(SignRecordBean bean) throws Exception {
        return signRecordDao.updateSignRecord(bean);
    }

    @Override
    public void deleteSignRecord(SignRecordBean bean) throws Exception {
        signRecordDao.deleteSignRecord(bean);
    }
}
