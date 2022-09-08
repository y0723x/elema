package com.yx.elema.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yx.elema.pojo.Shop;

import java.util.List;

public interface ShopMapper extends BaseMapper<Shop> {
    /**
     * 获取店铺通过menuId
     * @param menuId
     * @return
     */
    List<Shop> getShopByMenuId(Integer menuId);

    /**
     * 获取店铺详情通过shopId
     * @param shopId
     * @return
     */
    Shop getShopCateWithGoods(Integer shopId);
}
