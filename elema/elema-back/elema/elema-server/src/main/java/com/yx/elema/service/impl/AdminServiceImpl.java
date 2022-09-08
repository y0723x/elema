package com.yx.elema.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.elema.config.security.component.JwtTokenUtil;
import com.yx.elema.mapper.AdminMapper;
import com.yx.elema.pojo.Admin;
import com.yx.elema.pojo.RespBean;
import com.yx.elema.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",1));
    }

    /**
     * 返回登录信息
     * @param username
     * @param password
     * @return
     */
    @Override
    public RespBean login(String username, String password) {
        /**
         * 1.验证码检验（后面实现）
         * 2.用户名与密码检验
         * 3.更新security用户对象
         * 4.生成token（后面实现）
         */
        // 1....

        // 2.登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }

        System.out.println(userDetails.getUsername());

        // 3.更新
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 4.生成token
        String token = jwtTokenUtil.generateToken(username);
        Map<String, Object> claim = new HashMap<>();
        claim.put("tokenHead", tokenHead);
        claim.put("token", token);

        // 到时候这里会返回一个token让前端拥有登录标识
        return RespBean.success("登陆成功",claim);
    }
}
