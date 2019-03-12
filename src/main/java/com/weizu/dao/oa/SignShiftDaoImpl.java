package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.oa.SignShiftBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("signShiftDaoImpl")
public class SignShiftDaoImpl extends DaoSupport implements SignShiftDao {

    @Override
    public SignShiftBean findSignShiftById(SignShiftBean bean) throws Exception {
        return null;
    }

    @Override
    public List<SignShiftBean> findSignShiftByCondition(SignShiftBean bean) throws Exception {
        return null;
    }

    @Override
    public Integer insertSignShift(SignShiftBean bean) throws Exception {
        return null;
    }

    @Override
    public Integer updateSignShift(SignShiftBean bean) throws Exception {
        return null;
    }

    @Override
    public void deleteSignShift(SignShiftBean bean) throws Exception {

    }
}
