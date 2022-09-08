package com.yx.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.elema.pojo.Admin;
import com.yx.elema.pojo.RespBean;
import org.springframework.stereotype.Component;

public interface IAdminService extends IService<Admin> {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 返回登录信息
     * @param username
     * @param password
     * @return
     */
    RespBean login(String username, String password);
}
