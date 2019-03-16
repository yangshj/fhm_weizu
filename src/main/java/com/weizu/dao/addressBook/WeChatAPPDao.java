package com.weizu.dao.addressBook;

import com.weizu.pojo.addressBook.WeChatAPPBean;

import java.util.List;

/**
 * Description:
 *
 * @since : 2019/3/16 10:45:07
 **/
public interface WeChatAPPDao {

    /** 查找所有小程序app */
    public List<WeChatAPPBean> getAllWeChatApp() throws Exception;
}
