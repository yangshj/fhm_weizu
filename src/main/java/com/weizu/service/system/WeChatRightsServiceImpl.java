package com.weizu.service.system;

import com.weizu.dao.system.WeChatRightsDao;
import com.weizu.pojo.system.WechatRightsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("weChatRightsServiceImpl")
public class WeChatRightsServiceImpl implements WeChatRightsService {

    @Autowired
    private WeChatRightsDao weChatRightsDao;

    @Override
    public List<WechatRightsBean> getAllWeChatRights() throws Exception {
        return weChatRightsDao.getAllWeChatRights();
    }

    @Override
    public List<WechatRightsBean> getAllWeChatRightsByConditions(WechatRightsBean bean) throws Exception {
        return weChatRightsDao.getAllWeChatRightsByConditions(bean);
    }
}
