package com.yx.elema.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yx.elema.pojo.Menu;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private IMenuService menuService;

    /**
     * 首页获取一级菜单
     * @return
     */
    @GetMapping("/index")
    public RespBean getFirstLevelMenu(){
        return menuService.getFirstLevelMenu();
    }

    /**
     * 二级菜单
     * @return
     */
    @GetMapping("/index/{menuId}")
    public RespBean getSecondLevelMenu(@PathVariable Integer menuId){
        return menuService.getSecondLevelMenu(menuId);
    }
}
