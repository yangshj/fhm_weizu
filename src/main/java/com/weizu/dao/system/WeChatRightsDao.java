package com.weizu.dao.system;

import com.weizu.pojo.system.WechatRightsBean;

import java.util.List;

public interface WeChatRightsDao {

    /** 获取所有 */
    List<WechatRightsBean> getAllWeChatRights() throws Exception;

    /** 获取所有 */
    List<WechatRightsBean> getAllWeChatRightsByConditions(WechatRightsBean bean) throws Exception;
}
