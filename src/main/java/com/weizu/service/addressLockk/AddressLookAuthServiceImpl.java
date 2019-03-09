package com.weizu.service.addressLockk;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.weizu.dao.addressBook.AddressLookAuthDao;
import com.weizu.pojo.addressBook.AddressLookAuthRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("addressLookAuthServiceImpl")
public class AddressLookAuthServiceImpl implements  AddressLookAuthService{

    @Autowired
    private AddressLookAuthDao addressLookAuthDao;

    @Override
    public Integer insertAuthRequest(AddressLookAuthRequestBean bean) throws Exception {
        return addressLookAuthDao.insertAuthRequest(bean);
    }

    @Override
    public List<PageData> getAllAuthRequestlistPage(Page page) throws Exception {
        return addressLookAuthDao.getAllAuthRequestlistPage(page);
    }
}
