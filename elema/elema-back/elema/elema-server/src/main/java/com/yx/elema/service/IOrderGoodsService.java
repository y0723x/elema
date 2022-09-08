package com.yx.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.elema.pojo.OrderGoods;
import com.yx.elema.pojo.RespBean;

public interface IOrderGoodsService extends IService<OrderGoods> {
    /**
     * 通过orderId获取订单信息
     * @param orderId
     * @return
     */
    RespBean getOrderDetail(Integer orderId);
}
