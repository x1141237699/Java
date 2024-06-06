package com.xiao.oasystem.utils;

import com.xiao.oasystem.pojo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    private static final String SECRET_KEY = "xiao";
    private static final long PERIOD_OF_VALIDITY = 1000L * 60 * 60 * 24 * 30;

    //生成JWT令牌
    public static String creatToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("name", user.getName());
        claims.put("position",user.getPosition());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + PERIOD_OF_VALIDITY))
                .compact();
    }

    //解析JWT令牌
    public static Claims parseJWT(String JWT){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(JWT)
                .getBody();
    }
}
