package com.yx.elema.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yx.elema.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 获取所有历史订单记录
     * @return
     * @param id
     */
    List<Order> getAllOrders(Integer id);

    /**
     * 提交订单
     * @param order
     * @return
     */
    Integer submitOrder(@Param("order") Order order);

    /**
     * 确认付款
     * @param orderId
     * @return
     */
    Integer getOrderPay(Integer orderId);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    Integer getOrderCancel(Integer orderId);
}
