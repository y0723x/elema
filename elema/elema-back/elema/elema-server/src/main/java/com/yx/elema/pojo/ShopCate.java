package com.yx.elema.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class ShopCate {
    private int id;
    private String shopCateName;
    private String goodsIds;
    private int shopId;
    private int isEnabled;

    @TableField(exist = false)
    private List<Goods> goodsList;
}
