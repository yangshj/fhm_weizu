package com.weizu.dao.integral;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.integral.CommodityBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commodityDaoImpl")
public class CommodityDaoImpl extends DaoSupport implements CommodityDao {

    @Override
    public Integer insertCommodity(CommodityBean bean) throws Exception {
        return (Integer) this.save("com.weizu.commodity.insertCommodity", bean);
    }

    @Override
    public Integer updateCommodity(CommodityBean bean) throws Exception {
        return (Integer) this.update("com.weizu.commodity.updateCommodity",bean);
    }

    @Override
    public void deleteCommodity(CommodityBean bean) throws Exception {
        this.delete("com.weizu.commodity.deleteCommodity", bean);
    }

    @Override
    public List<CommodityBean> getCommodityByUserId(Long userId) throws Exception {
        return (List<CommodityBean>) this.findForList("com.weizu.commodity.getCommodityByUserId", userId);
    }

    @Override
    public List<CommodityBean> getCommodityByCondition(CommodityBean bean) throws Exception {
        return (List<CommodityBean>) this.findForList("com.weizu.commodity.getCommodityByCondition", bean);
    }

    @Override
    public List<CommodityBean> loadMoreByCondition(CommodityBean bean) throws Exception {
        return (List<CommodityBean>) this.findForList("com.weizu.commodity.loadMoreByCondition", bean);
    }
}
