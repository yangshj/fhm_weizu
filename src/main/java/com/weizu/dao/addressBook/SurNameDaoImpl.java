package com.weizu.dao.addressBook;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.addressBook.SurNameBean;

@Repository("surNameDaoImpl")
public class SurNameDaoImpl extends DaoSupport  implements SurNameDao{

	@Override
	public List<SurNameBean> getAllSurName() throws Exception {
		return (List<SurNameBean>) this.findForList("com.weizu.surName.getAllSurName", null);
	}

}
