package com.weizu.dao.addressBook;

import java.util.List;

import com.weizu.pojo.addressBook.SurNameBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;

public interface SurNameDao {
	
	public List<SurNameBean> getAllSurName(WeChatAPPBean bean) throws Exception;
}
