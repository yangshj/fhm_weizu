package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.oa.SignSchemeBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("signSchemeDaoImpl")
public class SignSchemeDaoImpl extends DaoSupport implements SignSchemeDao {
    @Override
    public SignSchemeBean findSignSchemeById(SignSchemeBean bean) throws Exception {
        return null;
    }

    @Override
    public List<SignSchemeBean> findSignSchemeByCondition(SignSchemeBean bean) throws Exception {
        return null;
    }

    @Override
    public Integer insertSignScheme(SignSchemeBean bean) throws Exception {
        return null;
    }

    @Override
    public Integer updateSignScheme(SignSchemeBean bean) throws Exception {
        return null;
    }

    @Override
    public void deleteSignScheme(SignSchemeBean bean) throws Exception {

    }
}
