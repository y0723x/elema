package com.yx.elema.controller;

import com.yx.elema.pojo.Order;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/orders")
    public RespBean getAllOrders(HttpServletRequest request){
        return orderService.getAllOrders(request);
    }

    /**
     * 1.添加t_order表
     * 2.添加t_order_goods表
     * @param shopId
     * @param order
     * @return
     */
    @PostMapping("/orderPay/{shopId}")
    public RespBean submitOrder(@PathVariable Integer shopId, @RequestBody Order order, HttpServletRequest request){
        return orderService.submitOrder(shopId,order, request);
    }

    /**
     * 确认支付
     * @param orderId
     * @return
     */
    @GetMapping("/orders/pay")
    public RespBean getOrderPay(@RequestParam("orderId") Integer orderId){
        return orderService.getOrderPay(orderId);
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/orders/cancel")
    public RespBean getOrderCancel(@RequestParam("orderId") Integer orderId){
        return orderService.getOrderCancel(orderId);
    }

}
