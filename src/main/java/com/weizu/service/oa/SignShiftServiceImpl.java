package com.weizu.service.oa;

import com.weizu.dao.oa.SignShiftDao;
import com.weizu.pojo.oa.SignShiftBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("signShiftServiceImpl")
public class SignShiftServiceImpl implements SignShiftService {

    @Autowired
    private SignShiftDao signShiftDao;

    @Override
    public SignShiftBean findSignShiftById(SignShiftBean bean) throws Exception {
        return signShiftDao.findSignShiftById(bean);
    }

    @Override
    public List<SignShiftBean> findSignShiftByCondition(SignShiftBean bean) throws Exception {
        return signShiftDao.findSignShiftByCondition(bean);
    }

    @Override
    public Integer insertSignShift(SignShiftBean bean) throws Exception {
        return signShiftDao.insertSignShift(bean);
    }

    @Override
    public Integer updateSignShift(SignShiftBean bean) throws Exception {
        return signShiftDao.updateSignShift(bean);
    }

    @Override
    public void deleteSignShift(SignShiftBean bean) throws Exception {
        signShiftDao.deleteSignShift(bean);
    }

    @Override
    public void deleteByTeamId(Long teamId) throws Exception {
        signShiftDao.deleteByTeamId(teamId);
    }
}
