package com.yx.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.elema.pojo.Menu;
import com.yx.elema.pojo.RespBean;

import java.util.List;

public interface IMenuService extends IService<Menu> {
    /**
     * 获取所有一级菜单
     * @return
     */
    RespBean getFirstLevelMenu();

    /**
     * 二级菜单
     * @param menuId
     * @return
     */
    RespBean getSecondLevelMenu(Integer menuId);
}
