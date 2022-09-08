package com.yx.elema.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Shop {
    private int id;
    private String shopName;
    private int parentId;
    private int isEnabled;
    private String catesIds;
    private String shopAddress;
    private double shopScore;
    private int shopSale;
    private int shopDistribution;
    private String shopUrl;
    private double distributionMinPrice;

    @TableField(exist = false)
    private List<ShopCate> shopCateList;
}
