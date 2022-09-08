package com.yx.elema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.elema.mapper.ShopMapper;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.pojo.Shop;
import com.yx.elema.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 获取店铺通过menuId
     * @param menuId
     * @return
     */
    @Override
    public RespBean getShopByMenuId(Integer menuId) {
        List<Shop> shopList = shopMapper.getShopByMenuId(menuId);
        if(shopList == null){
            return RespBean.error("获取shopList失败");
        }
        return RespBean.success("获取shopList成功", shopList);
    }

    /**
     * 获取店铺详细通过shopId
     * @param shopId
     * @return
     */
    @Override
    public RespBean getShopCateWithGoods(Integer shopId) {
        Shop shop = shopMapper.getShopCateWithGoods(shopId);
        if(shop == null){
            return RespBean.error("获取shop失败");
        }
        return RespBean.success("获取shop成功", shop);
    }
}
