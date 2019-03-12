package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.oa.SignShiftBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("signShiftDaoImpl")
public class SignShiftDaoImpl extends DaoSupport implements SignShiftDao {

    @Override
    public SignShiftBean findSignShiftById(SignShiftBean bean) throws Exception {
        return (SignShiftBean) this.findForObject("com.weizu.oa.signShift.findSignShiftById", bean);
    }

    @Override
    public List<SignShiftBean> findSignShiftByCondition(SignShiftBean bean) throws Exception {
        return (List<SignShiftBean>) this.findForList("com.weizu.oa.signShift.findSignShiftByCondition", bean);
    }

    @Override
    public Integer insertSignShift(SignShiftBean bean) throws Exception {
        return (Integer) this.save("com.weizu.oa.signShift.insertSignShift", bean);
    }

    @Override
    public Integer updateSignShift(SignShiftBean bean) throws Exception {
        return (Integer) this.update("com.weizu.oa.signShift.updateSignShift", bean);
    }

    @Override
    public void deleteSignShift(SignShiftBean bean) throws Exception {
        this.delete("com.weizu.oa.signShift.deleteSignShift", bean);
    }

    @Override
    public void deleteByTeamId(Long teamId) throws Exception {
        this.delete("com.weizu.oa.signShift.deleteSignShiftByTeamId", teamId);
    }
}
