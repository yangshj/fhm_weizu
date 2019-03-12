package com.weizu.dao.oa;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.oa.SignSchemeBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("signSchemeDaoImpl")
public class SignSchemeDaoImpl extends DaoSupport implements SignSchemeDao {

    @Override
    public SignSchemeBean findSignSchemeById(SignSchemeBean bean) throws Exception {
        return (SignSchemeBean) this.findForObject("com.weizu.oa.signScheme.findSignSchemeById", bean);
    }

    @Override
    public List<SignSchemeBean> findSignSchemeByCondition(SignSchemeBean bean) throws Exception {
        return (List<SignSchemeBean>) this.findForList("com.weizu.oa.signScheme.findSignSchemeByCondition", bean);
    }

    @Override
    public Integer insertSignScheme(SignSchemeBean bean) throws Exception {
        return (Integer) this.save("com.weizu.oa.signScheme.insertSignScheme", bean);
    }

    @Override
    public Integer updateSignScheme(SignSchemeBean bean) throws Exception {
        return (Integer) this.update("com.weizu.oa.signScheme.updateSignScheme", bean);
    }

    @Override
    public void deleteSignScheme(SignSchemeBean bean) throws Exception {
        this.delete("com.weizu.oa.signScheme.deleteSignScheme", bean);
    }
}
