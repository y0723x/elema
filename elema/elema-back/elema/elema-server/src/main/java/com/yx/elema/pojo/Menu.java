package com.yx.elema.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Menu {
    private int id;
    private int parentId;
    private String path;
    private int isParent;
    private int isEnabled;
    private String title;
    private String icon;
    private int level;

    @TableField(exist = false)
    private String parentTitle;
}
