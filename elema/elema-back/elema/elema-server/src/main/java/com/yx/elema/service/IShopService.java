package com.yx.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.pojo.Shop;

public interface IShopService extends IService<Shop> {
    /**
     * 获取店铺通过menuId
     * @param menuId
     * @return
     */
    RespBean getShopByMenuId(Integer menuId);

    /**
     * 获取店铺详细通过shopId
     * @param shopId
     * @return
     */
    RespBean getShopCateWithGoods(Integer shopId);
}
