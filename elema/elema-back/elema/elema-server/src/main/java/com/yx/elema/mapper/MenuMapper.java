package com.yx.elema.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yx.elema.pojo.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 获取所有一级菜单数据
     * @return
     */
    List<Menu> getFirstLevelMenu();

    /**
     * 二级菜单
     * @param menuId
     * @return
     */
    List<Menu> getSecondLevelMenu(Integer menuId);
}
