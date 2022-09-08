package com.yx.elema.controller;

import com.yx.elema.pojo.AdminLoginParam;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    /**
     * 1.验证用户的密码是否正确
     * 2.验证码是否正确，通过前端session获取，后面实现
     * @param params
     * @return
     */
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam params){
        return adminService.login(params.getUsername(),params.getPassword());
    }

}
