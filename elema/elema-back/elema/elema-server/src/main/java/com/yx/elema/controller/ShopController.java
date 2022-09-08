package com.yx.elema.controller;

import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private IShopService shopService;

    /**
     * 获取店铺通过menuId
     * @param menuId
     * @return
     */
    @GetMapping
    public RespBean getShopByMenuId(@RequestParam Integer menuId){
        return shopService.getShopByMenuId(menuId);
    }

    /**
     * 获取店铺详细通过shopId
     * @param shopId
     * @return
     */
    @GetMapping("/{shopId}")
    public RespBean getShopCateWithGoods(@PathVariable Integer shopId){
        return shopService.getShopCateWithGoods(shopId);
    }
}
