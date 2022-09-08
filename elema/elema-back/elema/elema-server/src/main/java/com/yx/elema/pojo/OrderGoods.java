package com.yx.elema.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class OrderGoods {
    private int id;
    private int orderId;
    private int goodsId;
    private int isEnabled;
    private int goodsCount;

    @TableField(exist = false)
    private Goods goods;

    @TableField(exist = false)
    private Shop shop;

    @TableField(exist = false)
    private Order order;
}
