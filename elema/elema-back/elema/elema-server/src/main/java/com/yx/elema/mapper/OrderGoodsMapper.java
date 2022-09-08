package com.yx.elema.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yx.elema.pojo.OrderGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {
    /**
     * 通过orderId获取订单信息
     * @param orderId
     * @return
     */
    List<OrderGoods> getOrderDetail(Integer orderId);

    /**
     * 提交订单
     * @param id
     * @param orderGoodsList
     * @return
     */
    Integer submitOrder(@Param("id") int id, @Param("orderGoodsList") List<OrderGoods> orderGoodsList);
}
