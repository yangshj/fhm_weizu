package com.weizu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weizu.dao.SurNameDao;
import com.weizu.pojo.SurNameBean;

@Service("surNameServiceImpl")
public class SurNameServiceImpl implements SurNameService{
	
	@Autowired
	private SurNameDao surNameDao;
	
	@Override
	public List<SurNameBean> getAllSurName() throws Exception {
		return surNameDao.getAllSurName();
	}
}
