package com.util;


import com.pojo.User;
import com.service.UserListService;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUitls {
    public static Map<String,String> tokenMp = new HashMap<>();
    /**
     * 01为登录token时效期
     * 01为验证码时效期
     */
    private static final long EXPIRE_TIME01 =30*60*1000;
    private static final long EXPIRE_TIME02=60*1000;
    /**
     * 加密密钥
     */
    private static final String KEY = "chenxiang";

    /**
     * 生成账号密码的token
     *
     * @return
     */
    public static String createToken(String account,String password){
        Map<String,Object> header = new HashMap();
        Map<String,Object> Claims = new HashMap();

        Claims.put("account", account);
        Claims.put(("password"), password);

        header.put("typ","JWT");
        header.put("alg","HS256");
        //setID:用户ID
        //setExpiration:token过期时间  当前时间+有效时间
        //setSubject:用户名
        //setIssuedAt:token创建时间
        //signWith:加密方式
        JwtBuilder builder = Jwts.builder()
                .setClaims(Claims)
                .setHeader(header)
                .setId(account)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRE_TIME01))
                .setSubject(account)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,KEY);
        String token = builder.compact();
        tokenMp.put(account,token);
        return token;
    }

    /**
     * 生成邮箱验证码的token
     * @param email
     * @return
     */
    public static String createToken(String email){
        Map<String,Object> header = new HashMap();
        Map<String,Object> Claims = new HashMap();

        Claims.put("email", email);
        header.put("typ","JWT");
        header.put("alg","HS256");
        //setID:用户ID
        //setExpiration:token过期时间  当前时间+有效时间
        //setSubject:用户名
        //setIssuedAt:token创建时间
        //signWith:加密方式
        JwtBuilder builder = Jwts.builder()
                .setClaims(Claims)
                .setHeader(header)
                .setId(email)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRE_TIME02))
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,KEY);
        return builder.compact();
    }

    /**
     * 验证验证码的token是否在有效期内
     * @param token
     * @return
     */
    public static boolean verifyVCToken(String token){
        Claims claims = null;
        try {
            //token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，
            claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return false;
        }
        return true;
    }

    /**
     * 验证token是否有效
     * @param token  请求头中携带的token
     * @return  token验证结果  2-token过期；1-token认证通过；0-token认证失败
     */
    public static User verify(String token){
        if(token==null){
            return null;
        }
        Claims claims = null;
        try {
            //token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，
            claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
          return null;
        }
        //从token中获取用户account，在服务器的map当查询是否当前用户是否token有效
        String account = claims.get("account", String.class);
        String token2 = tokenMp.get(account);
        if(token2!=null && token2.equals(token)){
            User user = UserListService.selectByAccount(account);
            return user;
        }else{
            return null;
        }
    }
}
