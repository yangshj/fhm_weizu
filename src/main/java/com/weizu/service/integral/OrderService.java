package com.weizu.service.integral;

import com.weizu.pojo.integral.OrderBean;
import com.weizu.pojo.integral.OrderInfoBean;

import java.util.List;

public interface OrderService {

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

    /** 通过条件获取订单 */
    List<OrderInfoBean> getOrderListByCondition(OrderInfoBean bean) throws Exception;
}
