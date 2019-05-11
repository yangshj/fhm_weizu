package com.weizu.dao.integral;

import com.weizu.pojo.integral.OrderBean;

import java.util.List;

public interface OrderDao {

    /** 插入订单 */
    Integer insertOrder(OrderBean bean) throws Exception;

    /** 获取订单 */
    OrderBean getOrderById(Long id) throws Exception;

    /** 修改订单 */
    Integer updateOrder(OrderBean bean) throws Exception;

    /** 删除订单 */
    void deleteOrder(OrderBean bean) throws Exception;

    /** 通过用户id获取订单 */
    List<OrderBean> getOrderByUserId(Long userId) throws Exception;

    /** 通过条件获取订单 */
    List<OrderBean> getOrderByCondition(OrderBean bean) throws Exception;

}
