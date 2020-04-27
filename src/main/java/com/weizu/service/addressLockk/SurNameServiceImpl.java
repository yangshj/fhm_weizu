package com.weizu.service.addressLockk;

import java.util.List;

import com.weizu.pojo.addressBook.WeChatAPPBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weizu.dao.addressBook.SurNameDao;
import com.weizu.pojo.addressBook.SurNameBean;

@Service("surNameServiceImpl")
public class SurNameServiceImpl implements SurNameService{
	
	@Autowired
	private SurNameDao surNameDao;
	
	@Override
	public List<SurNameBean> getAllSurName(WeChatAPPBean bean) throws Exception {
		return surNameDao.getAllSurName(bean);
	}

	@Override
	public Integer updateSurName(SurNameBean bean) throws Exception {
		return surNameDao.updateSurName(bean);
	}

	@Override
	public Integer insertSurName(SurNameBean bean) throws Exception {
		return surNameDao.insertSurName(bean);
	}
}
