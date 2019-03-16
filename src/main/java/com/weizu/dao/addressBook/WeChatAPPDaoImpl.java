package com.weizu.dao.addressBook;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("weChatAPPDaoImpl")
public class WeChatAPPDaoImpl  extends DaoSupport implements WeChatAPPDao{

    @Override
    public List<WeChatAPPBean> getAllWeChatApp() throws Exception {
        return (List<WeChatAPPBean>) this.findForList("com.weizu.weChatApp.getAllWeChatApp",null);
    }
}
