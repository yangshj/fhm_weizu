package com.weizu.dao.integral;

import com.fh.dao.DaoSupport;
import com.weizu.pojo.integral.OrderBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderDaoImpl")
public class OrderDaoImpl extends DaoSupport implements OrderDao {

    @Override
    public Integer insertOrder(OrderBean bean) throws Exception {
        return (Integer) this.save("com.weizu.order.insertOrder", bean);
    }

    @Override
    public OrderBean getOrderById(Long id) throws Exception {
        return (OrderBean) this.findForObject("com.weizu.order.getOrderById",id);
    }

    @Override
    public Integer updateOrder(OrderBean bean) throws Exception {
        return (Integer) this.update("com.weizu.order.updateOrder",bean);
    }

    @Override
    public void deleteOrder(OrderBean bean) throws Exception {
        this.delete("com.weizu.order.deleteOrder", bean);
    }

    @Override
    public List<OrderBean> getOrderByUserId(Long userId) throws Exception {
        return (List<OrderBean>) this.findForList("com.weizu.order.getOrderByUserId", userId);
    }

    @Override
    public List<OrderBean> getOrderByCondition(OrderBean bean) throws Exception {
        return (List<OrderBean>) this.findForList("com.weizu.order.getOrderByCondition", bean);
    }
}
