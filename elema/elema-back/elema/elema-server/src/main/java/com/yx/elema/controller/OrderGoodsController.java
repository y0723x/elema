package com.yx.elema.controller;

import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IOrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderGoodsController {

    @Autowired
    private IOrderGoodsService orderGoodsService;

    @GetMapping("/orders/{orderId}")
    public RespBean getOrderDetail(@PathVariable Integer orderId){
        return orderGoodsService.getOrderDetail(orderId);
    }
}
