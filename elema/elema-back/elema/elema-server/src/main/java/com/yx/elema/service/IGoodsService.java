package com.yx.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.elema.pojo.Goods;

import java.util.List;

public interface IGoodsService extends IService<Goods> {
    /**
     * 通过goodsIds获取goodsList
     * @param productIds
     * @return
     */
    List<Goods> getGoodsByGoodsIds(Integer[] productIds);
}
