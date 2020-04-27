package com.weizu.service.addressLockk;

import java.util.List;

import com.weizu.pojo.addressBook.SurNameBean;
import com.weizu.pojo.addressBook.WeChatAPPBean;

public interface SurNameService {
	
	
	List<SurNameBean> getAllSurName(WeChatAPPBean bean) throws Exception;

	Integer updateSurName(SurNameBean bean) throws Exception;

	Integer insertSurName(SurNameBean bean) throws Exception;
	
}
