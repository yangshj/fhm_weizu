package com.weizu.service.integral;

import com.weizu.dao.integral.CommodityDao;
import com.weizu.pojo.integral.CommodityBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commodityServiceImpl")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityDao commodityDao;

    @Override
    public Integer insertCommodity(CommodityBean bean) throws Exception {
        return commodityDao.insertCommodity(bean);
    }

    @Override
    public Integer updateCommodity(CommodityBean bean) throws Exception {
        return commodityDao.updateCommodity(bean);
    }

    @Override
    public void deleteCommodity(Long id) throws Exception {
        commodityDao.deleteCommodity(id);
    }

    @Override
    public void deleteCommodityLogic(Long id) throws Exception {
        commodityDao.deleteCommodityLogic(id);
    }

    @Override
    public List<CommodityBean> getCommodityByUserId(Long userId) throws Exception {
        return commodityDao.getCommodityByUserId(userId);
    }

    @Override
    public List<CommodityBean> getCommodityByCondition(CommodityBean bean) throws Exception {
        return commodityDao.getCommodityByCondition(bean);
    }

    @Override
    public List<CommodityBean> loadMoreByCondition(CommodityBean bean) throws Exception {
        return commodityDao.loadMoreByCondition(bean);
    }
}
