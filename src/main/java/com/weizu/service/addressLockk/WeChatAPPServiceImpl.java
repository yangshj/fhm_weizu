package com.weizu.service.addressLockk;

import com.weizu.dao.addressBook.WeChatAPPDao;
import com.weizu.pojo.addressBook.WeChatAPPBean;
import com.weizu.service.addressLockk.WeChatAPPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("weChatAPPServiceImpl")
public class WeChatAPPServiceImpl implements WeChatAPPService {

    @Autowired
    private WeChatAPPDao weChatAPPDao;

    @Override
    public List<WeChatAPPBean> getAllWeChatApp() throws Exception {
        return weChatAPPDao.getAllWeChatApp();
    }
}
