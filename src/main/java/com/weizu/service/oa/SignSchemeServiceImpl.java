package com.weizu.service.oa;

import com.weizu.dao.oa.SignSchemeDao;
import com.weizu.pojo.oa.SignSchemeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("signSchemeServiceImpl")
public class SignSchemeServiceImpl implements SignSchemeService {

    @Autowired
    private SignSchemeDao signSchemeDao;

    @Override
    public SignSchemeBean findSignSchemeById(SignSchemeBean bean) throws Exception {
        return signSchemeDao.findSignSchemeById(bean);
    }

    @Override
    public List<SignSchemeBean> findSignSchemeByCondition(SignSchemeBean bean) throws Exception {
        return signSchemeDao.findSignSchemeByCondition(bean);
    }

    @Override
    public Integer insertSignScheme(SignSchemeBean bean) throws Exception {
        return signSchemeDao.insertSignScheme(bean);
    }

    @Override
    public Integer updateSignScheme(SignSchemeBean bean) throws Exception {
        return signSchemeDao.updateSignScheme(bean);
    }

    @Override
    public void deleteSignScheme(SignSchemeBean bean) throws Exception {
        signSchemeDao.deleteSignScheme(bean);
    }

    @Override
    public void deleteByTeamId(Long teamId) throws Exception {
        signSchemeDao.deleteByTeamId(teamId);
    }
}
