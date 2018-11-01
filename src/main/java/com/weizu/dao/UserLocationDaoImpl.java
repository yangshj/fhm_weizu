package com.weizu.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.UserLocationMarkerBean;
import com.weizu.pojo.WeiZuLocationBean;

@Repository("userLocationDaoImpl")
public class UserLocationDaoImpl extends DaoSupport implements UserLocationDao{
	
	@Override
	public Integer insertLocation(WeiZuLocationBean bean) throws Exception {
		return (Integer) this.save("com.weizu.userLocation.insertLocation", bean);
	}

	@Override
	public List<UserLocationMarkerBean> getAllUserLatelyLocaitons() throws Exception {
		return (List<UserLocationMarkerBean>) this.findForList("com.weizu.userLocation.getAllUserLatelyLocaitons", null);
	}
}
