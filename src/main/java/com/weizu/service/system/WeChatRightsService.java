package com.weizu.service.system;

import com.weizu.pojo.system.WechatRightsBean;

import java.util.List;

public interface WeChatRightsService {

    /** 获取所有 */
    List<WechatRightsBean> getAllWeChatRights() throws Exception;

    /** 获取所有 */
    List<WechatRightsBean> getAllWeChatRightsByConditions(WechatRightsBean bean) throws Exception;

}
