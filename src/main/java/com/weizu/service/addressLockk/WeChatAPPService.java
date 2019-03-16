package com.weizu.service.addressLockk;


import com.weizu.pojo.addressBook.WeChatAPPBean;

import java.util.List;

public interface WeChatAPPService {

    /** 查找所有小程序app */
    public List<WeChatAPPBean> getAllWeChatApp() throws Exception;
}
