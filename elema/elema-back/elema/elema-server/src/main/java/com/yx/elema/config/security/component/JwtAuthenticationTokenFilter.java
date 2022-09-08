package com.yx.elema.config.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * jwt登录授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取token请求头
        String authHeader = request.getHeader(tokenHeader);

        // 存在token,判断请求头是否存在和jwt请求头的匹配
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // 去除jwt请求头，获取token
            String token = authHeader.substring(tokenHead.length());
            // 通过token获取用户名
            String username = jwtTokenUtil.getUserNameByToken(token);
            // 用户名存在，但后端securityContext并未实现登录状态
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 实现登录认证
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 判断token是否失效
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    // token过期，重新实现登录状态
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
