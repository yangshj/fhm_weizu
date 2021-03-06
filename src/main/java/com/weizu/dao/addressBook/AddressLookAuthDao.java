package com.weizu.dao.addressBook;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.pojo.addressBook.AddressLookAuthRequestBean;

import java.util.List;

public interface AddressLookAuthDao {


    /** 插入授权记录 */
    Integer insertAuthRequest(AddressLookAuthRequestBean bean) throws Exception;

    /** 获取授权记录--页面 */
    List<PageData> getAllAuthRequestlistPage(Page page) throws Exception;
}
