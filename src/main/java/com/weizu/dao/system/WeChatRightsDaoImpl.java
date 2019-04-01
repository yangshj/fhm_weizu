package com.weizu.dao.system;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.system.WechatRightsBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("weChatRightsDaoImpl")
public class WeChatRightsDaoImpl extends DaoSupport implements WeChatRightsDao {


    @Override
    public List<WechatRightsBean> getAllWeChatRights() throws Exception {
        return (List<WechatRightsBean>) this.findForList("com.weizu.weChatRights.getAllWeChatRights", null);
    }

    @Override
    public List<WechatRightsBean> getAllWeChatRightsByConditions(WechatRightsBean bean) throws Exception {
        return (List<WechatRightsBean>) this.findForList("com.weizu.weChatRights.getAllWeChatRights", bean);
    }
}
