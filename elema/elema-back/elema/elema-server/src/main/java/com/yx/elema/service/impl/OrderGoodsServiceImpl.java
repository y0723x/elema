package com.yx.elema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.elema.mapper.OrderGoodsMapper;
import com.yx.elema.pojo.Menu;
import com.yx.elema.pojo.OrderGoods;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IOrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderGoodsServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements IOrderGoodsService {

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    /**
     * 通过orderId获取订单信息
     * @param orderId
     * @return
     */
    @Override
    public RespBean getOrderDetail(Integer orderId) {
        List<OrderGoods> orderGoodsList = orderGoodsMapper.getOrderDetail(orderId);
        if (orderGoodsList == null) {
            return RespBean.error("获取orderGoodsList失败");
        }
        return RespBean.success("获取orderGoodsList成功", orderGoodsList);
    }
}
