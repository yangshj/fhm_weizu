package com.weizu.dao.addressBook;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.addressBook.UserLocationMarkerBean;
import com.weizu.pojo.addressBook.WeiZuLocationBean;

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

	@Override
	public List<PageData> getLocationListPage(Page page)  throws Exception {
		return (List<PageData>) this.findForList("com.weizu.userLocation.getLocationlistPage", page);
	}
}
