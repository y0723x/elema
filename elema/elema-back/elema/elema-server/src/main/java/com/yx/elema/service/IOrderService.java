package com.yx.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.elema.pojo.Order;
import com.yx.elema.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

public interface IOrderService extends IService<Order> {
    /**
     * 获取所有历史订单记录
     * @Param request
     * @return
     */
    RespBean getAllOrders(HttpServletRequest request);

    /**
     * 1.添加t_order表
     * 2.添加t_order_goods表
     * @param shopId
     * @param order
     * @Param request
     * @return
     */
    RespBean submitOrder(Integer shopId, Order order, HttpServletRequest request);

    /**
     * 确认付款
     * @param orderId
     * @return
     */
    RespBean getOrderPay(Integer orderId);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    RespBean getOrderCancel(Integer orderId);
}
