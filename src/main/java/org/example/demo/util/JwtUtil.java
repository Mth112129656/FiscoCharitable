package org.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class JwtUtil {
    /**
     * 密钥
     */
    private static final String SECRET = "FiscoCharitable";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 1000 * 60 * 60 * 24;//单位为毫秒

    /**
     * 生成用户token,设置token超时时间
     */
    public static String createToken(String email) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)// 添加头部
                //可以将基本信息放到claims中
                .withClaim("email", email)//email
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }


    /**
     * 校验token,由于未涉及角色权限，因此不需要解析
     *
     * @param token
     * @return
     */
    public static boolean verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            jwt = parseJwt(token);
            //decodedJWT.getClaim("属性").asString()  获取负载中的属性值
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("token解码异常");
            //解码异常则抛出异常
            return false;
        }
        return true;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static DecodedJWT parseJwt(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }
}
