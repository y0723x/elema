package com.yx.elema.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yx.elema.pojo.Goods;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 通过goodsIds生成goodsIds
     * @param productIds
     * @return
     */
    List<Goods> getGoodsByGoodsIds(Integer[] productIds);
}
