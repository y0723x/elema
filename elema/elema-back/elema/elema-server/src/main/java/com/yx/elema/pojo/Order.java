package com.yx.elema.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    private int id;
    private int adminId;
    private int shopId;
    private String goodsIds;
    private Date createTime;
    private int status;
    private int isEnabled;
    private double totalPrice;
    private int totalCount;

    @TableField(exist = false)
    private Shop shop;

    @TableField(exist = false)
    private List<Goods> goodsList;
}
