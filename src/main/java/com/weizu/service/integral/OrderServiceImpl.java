package com.weizu.service.integral;

import com.weizu.dao.integral.OrderDao;
import com.weizu.pojo.integral.OrderBean;
import com.weizu.pojo.integral.OrderInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Integer insertOrder(OrderBean bean) throws Exception {
        return orderDao.insertOrder(bean);
    }

    @Override
    public OrderBean getOrderById(Long id) throws Exception {
        return orderDao.getOrderById(id);
    }

    @Override
    public Integer updateOrder(OrderBean bean) throws Exception {
        return orderDao.updateOrder(bean);
    }

    @Override
    public void deleteOrder(OrderBean bean) throws Exception {
        orderDao.deleteOrder(bean);
    }

    @Override
    public List<OrderBean> getOrderByUserId(Long userId) throws Exception {
        return orderDao.getOrderByUserId(userId);
    }

    @Override
    public List<OrderBean> getOrderByCondition(OrderBean bean) throws Exception {
        return orderDao.getOrderByCondition(bean);
    }

    @Override
    public List<OrderInfoBean> getOrderListByCondition(OrderInfoBean bean) throws Exception {
        return orderDao.getOrderListByCondition(bean);
    }
}
