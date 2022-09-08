package com.yx.elema.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.elema.config.security.component.JwtTokenUtil;
import com.yx.elema.mapper.AdminMapper;
import com.yx.elema.mapper.GoodsMapper;
import com.yx.elema.mapper.OrderGoodsMapper;
import com.yx.elema.mapper.OrderMapper;
import com.yx.elema.pojo.*;
import com.yx.elema.service.IAdminService;
import com.yx.elema.service.IGoodsService;
import com.yx.elema.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    /**
     * 获取所有历史订单记录
     * @return
     */
    @Override
    public RespBean getAllOrders(HttpServletRequest request) {
        // 获取username
        String authorization = request.getHeader(tokenHeader);
        String token = authorization.substring(tokenHead.length());

        // 通过token生成username
        String username = jwtTokenUtil.getUserNameByToken(token);
        // 通过username获取adminId
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",1));
        if (admin == null) {
            return RespBean.error("尚未登陆，请登录");
        }

        // 获取登陆状态的用户orderList
        List<Order> orderList = orderMapper.getAllOrders(admin.getId());
        // 获取的goods_ids转换成goodsList
        for (Order o:orderList
             ) {
            String goodsIds = o.getGoodsIds();
            // 将String类型goodsIds转换成Integer数组
            String[] goods_ids = goodsIds.split(",");
            Integer[] productIds = new Integer[goods_ids.length];
            for (int i = 0; i < goods_ids.length; i++) {
                productIds[i] = Integer.parseInt(goods_ids[i]);
            }
            // 通过goodsIds生成goodsList
            List<Goods> goodsList = goodsMapper.getGoodsByGoodsIds(productIds);
            o.setGoodsList(goodsList);
        }
        if(orderList == null){
            return RespBean.error("获取orderList失败");
        }

        return RespBean.success("获取orderList成功", orderList);
    }

    /**
     * 1.添加t_order表
     * 2.添加t_order_goods表
     * @param shopId
     * @param order
     * @param request
     * @return
     */
    @Override
    public RespBean submitOrder(Integer shopId, Order order, HttpServletRequest request) {
        /**
         * 1.数据接收
         * 2.数据转化
         * 3.业务逻辑
         * 4.返回结果
         */
        // 获取username
        String authorization = request.getHeader(tokenHeader);
        String token = authorization.substring(tokenHead.length());

        // 通过token生成username
        String username = jwtTokenUtil.getUserNameByToken(token);
        // 通过username获取adminId
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",1));
        if (admin == null) {
            return RespBean.error("尚未登陆，请登录");
        }

        // 获取goodsList
        List<Goods> goodsList = order.getGoodsList();
        if (goodsList == null) {
            return RespBean.error("数据接收错误，请检验");
        }

        // 获取goodsIds和orderGoodsList
        String goodsIds = "";
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            Goods goods = goodsList.get(i);
            OrderGoods orderGoods = new OrderGoods();
            if (i != (goodsList.size()-1)){
                goodsIds += goods.getId() + ",";
                orderGoods.setId(i);
                orderGoods.setOrderId(i);
                orderGoods.setIsEnabled(1);
                orderGoods.setGoodsId(goods.getId());
                orderGoods.setGoodsCount(goods.getCount());
                orderGoodsList.add(orderGoods);
            }else{
                goodsIds += goods.getId();
                orderGoods.setId(i);
                orderGoods.setOrderId(i);
                orderGoods.setIsEnabled(1);
                orderGoods.setGoodsId(goods.getId());
                orderGoods.setGoodsCount(goods.getCount());
                orderGoodsList.add(orderGoods);
            }
        }

        // 业务逻辑
        order.setAdminId(admin.getId());
        order.setGoodsIds(goodsIds);
        order.setIsEnabled(1);
        order.setCreateTime(new Date());
        order.setShopId(shopId);

        // 添加t_order表
        Integer result = orderMapper.submitOrder(order);
        if (result == 0) {
            return RespBean.error("添加order失败");
        }
        // 获取新添加的order
        List<Order> orderList = orderMapper.getAllOrders(admin.getId());
        Order lastOrder = orderList.get(orderList.size()-1);
        // 添加t_order_goods表
        Integer res = orderGoodsMapper.submitOrder(lastOrder.getId(), orderGoodsList);
        if (res != orderGoodsList.size()) {
            return RespBean.error("添加orderGoods失败");
        }

        /**
         * 注意：
         * 1.按理来说下单增加后会增加店铺的月售、评分啥的，但这里并未写到，以后添加
         */
        lastOrder.setShopId(shopId);
        return RespBean.success("添加成功", lastOrder);
    }

    /**
     * 确认付款
     * @param orderId
     * @return
     */
    @Override
    public RespBean getOrderPay(Integer orderId) {
        if (orderId == null || orderId == 0) {
            return RespBean.error("订单号不能为空");
        }
        Integer result = orderMapper.getOrderPay(orderId);
        if (result == 0) {
            return RespBean.error("支付失败");
        }
        return RespBean.success("支付成功");
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @Override
    public RespBean getOrderCancel(Integer orderId) {
        if (orderId == 0) {
            return RespBean.error("订单号不能为空");
        }
        Integer result = orderMapper.getOrderCancel(orderId);
        if (result == 0) {
            return RespBean.error("取消订单失败");
        }
        return RespBean.success("取消订单成功");
    }
}
