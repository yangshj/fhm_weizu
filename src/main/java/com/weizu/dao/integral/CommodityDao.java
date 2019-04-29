package com.weizu.dao.integral;

import com.weizu.pojo.integral.CommodityBean;
import com.weizu.pojo.other.ImageTextBean;

import java.util.List;

public interface CommodityDao {

    /** 插入商品 */
    Integer insertCommodity(CommodityBean bean) throws Exception;

    /** 修改商品 */
    Integer updateCommodity(CommodityBean bean) throws Exception;

    /** 删除商品 */
    void deleteCommodity(CommodityBean bean) throws Exception;

    /** 通过用户id获取商品 */
    List<CommodityBean> getCommodityByUserId(Long userId) throws Exception;

    /** 通过条件获取商品 */
    List<CommodityBean> getCommodityByCondition(CommodityBean bean) throws Exception;

    /** 对应小程序加载更多 */
    List<CommodityBean> loadMoreByCondition(CommodityBean bean) throws Exception;
}
