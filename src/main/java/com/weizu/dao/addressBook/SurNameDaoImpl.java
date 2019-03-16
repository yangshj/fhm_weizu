package com.weizu.dao.addressBook;

import java.util.List;

import com.weizu.pojo.addressBook.WeChatAPPBean;
import org.springframework.stereotype.Repository;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.addressBook.SurNameBean;

@Repository("surNameDaoImpl")
public class SurNameDaoImpl extends DaoSupport  implements SurNameDao{

	@Override
	public List<SurNameBean> getAllSurName(WeChatAPPBean bean) throws Exception {
		return (List<SurNameBean>) this.findForList("com.weizu.surName.getAllSurName", bean);
	}

}
