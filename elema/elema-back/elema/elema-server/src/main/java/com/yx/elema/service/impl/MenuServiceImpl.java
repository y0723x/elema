package com.yx.elema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.elema.mapper.MenuMapper;
import com.yx.elema.pojo.Menu;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 一级菜单
     * @return
     */
    @Override
    public RespBean getFirstLevelMenu() {
        List<Menu> menuList = menuMapper.getFirstLevelMenu();
        if (menuList == null) {
            return RespBean.error("获取cateList失败");
        }
        return RespBean.success("获取cateList成功", menuList);
    }

    /**
     * 二级菜单
     * @param menuId
     * @return
     */
    @Override
    public RespBean getSecondLevelMenu(Integer menuId) {
        List<Menu> menuList = menuMapper.getSecondLevelMenu(menuId);
        if (menuList == null) {
            return RespBean.error("获取cateList失败");
        }
        return RespBean.success("获取cateList成功", menuList);
    }
}
