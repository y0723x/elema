package com.yx.elema.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Goods {
    private int id;
    private double goodsPrice;
    private String goodsIntro;
    private String goodsName;
    private String goodsUrl;
    private int goodsSale;
    private int shopCateId;
    private int isEnabled;

    @TableField(exist = false)
    private int count;
}
