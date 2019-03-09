package com.weizu.service.addressLockk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weizu.dao.addressBook.SurNameDao;
import com.weizu.pojo.addressBook.SurNameBean;

@Service("surNameServiceImpl")
public class SurNameServiceImpl implements SurNameService{
	
	@Autowired
	private SurNameDao surNameDao;
	
	@Override
	public List<SurNameBean> getAllSurName() throws Exception {
		return surNameDao.getAllSurName();
	}
}
