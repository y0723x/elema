package com.yx.elema.config.security.component;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken工具类
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    // 秘钥
    @Value("${jwt.secret}")
    private String secret;

    // 过期时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 通过荷载生成token
     * @param claim
     * @return
     */
    public String generateToken(Map<String, Object> claim){
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(generateExpiration())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 生成token过期时间
     * @return
     */
    private Date generateExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 通过token获取荷载
     * @param token
     * @return
     */
    public Claims getClaimByToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 通过用户名生成荷载，并得到token
     * @param username
     * @return
     */
    public String generateToken(String username){
        // 创建荷载
        Map<String, Object> claim = new HashMap<>();
        claim.put(CLAIM_KEY_USERNAME, username);
        claim.put(CLAIM_KEY_CREATED, new Date());
        // 生成token
        return generateToken(claim);
    }

    /**
     * 通过token获取用户名
     * @param token
     * @return
     */
    public String getUserNameByToken(String token){
        String username;
        try {
            Claims claims = getClaimByToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断token是否失效
     * 1.当前用户名与userdetails.loadbyusername()中是否相等
     * 2.是否过期
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserNameByToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 与当前时间比较
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expired = getExpiredByToken(token);
        return expired.before(new Date());
    }

    /**
     * 获取过期时间
     * @param token
     * @return
     */
    public Date getExpiredByToken(String token){
        Claims claims = getClaimByToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断是否过期，并能刷新token
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 更新荷载中的过期时间
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimByToken(token);
        claims.setExpiration(new Date());
        return generateToken(claims);
    }
}
