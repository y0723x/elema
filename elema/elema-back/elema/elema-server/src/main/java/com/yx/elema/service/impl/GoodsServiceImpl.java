package com.yx.elema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.elema.mapper.GoodsMapper;
import com.yx.elema.pojo.Goods;
import com.yx.elema.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 通过goodsIds生成goodsList
     * @param productIds
     * @return
     */
    @Override
    public List<Goods> getGoodsByGoodsIds(Integer[] productIds) {
        return goodsMapper.getGoodsByGoodsIds(productIds);
    }
}
