package com.yx.elema.config.security;

import com.yx.elema.config.security.component.JwtAuthenticationTokenFilter;
import com.yx.elema.config.security.component.RestAccessDeniedHandler;
import com.yx.elema.config.security.component.RestAuthorizationEntryPoint;
import com.yx.elema.pojo.Admin;
import com.yx.elema.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Lazy
    @Autowired
    private IAdminService adminService;

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public PasswordEncoder getPassWord(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置登录认证
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            // 根据用户名获取用户信息
            Admin admin = adminService.getAdminByUserName(username);
            if (admin != null) {
                return admin;
            }
            throw new UsernameNotFoundException("用户名或密码不正确");
        };
    }

    /**
     * 允许不通过授权认证而获取请求的页面及方法
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/login");
    }

    /**
     * 调用自定义的userdetails和passencoder方法
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(getPassWord());
    }

    /**
     * 1.开启授权认证，任何页面和请求都需要经过认证才能访问
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义登录页面
        // http.formLogin()
                // .loginPage("/login/login.html");
                // 必须为post请求
                // .successForwardUrl("/toMain");

        // 关闭防火墙
        http.csrf()
                .disable()
                // 开始请求权限
                .authorizeRequests()
                // 对所有http请求都需要授权认证
                .anyRequest()
                .authenticated();

        // 添加jwt，登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 自定义未登录和权限不足页面
        http.exceptionHandling()
                .authenticationEntryPoint(restAuthorizationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler);
    }
}
