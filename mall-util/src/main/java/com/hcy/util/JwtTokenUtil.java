package com.hcy.util;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    private static final String SALT = "salt";
    public static String getToken(Map<String,Object> map){
        long en = System.currentTimeMillis();
        long expire = en + 24*60*60*1000; // 1 天有效期
        Date exp = new Date(expire);  // 这就是 1天之后的时间
        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置 jwt 的 标准声明，就是有效负载信息
                .setId("1001")  // id 唯一身份标识
                .setSubject("common-user") // jwt面向的用户
                .setIssuedAt(new Date())  //声明的签发时间
                // 设置签名的手段: 算法和密钥， 算法会放在header里
                .signWith(SignatureAlgorithm.HS256, SALT.getBytes()) // 声明的算法和盐
                .addClaims(map)  // 添加自定义说明
                .setExpiration(exp);  // 设置过期时间为1 天，如果不设置过期时间，默认是永不过期

        // 最终形成的token
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String,Object> verify(String token){
        HashMap<String,Object> result = new HashMap<>();
        Claims claims = null;
        try {
            claims = (Claims) Jwts.parser()
                    .setSigningKey(SALT.getBytes())  // 与生成token时指定的密钥一样
                    .parse(token)
                    .getBody();

            JwtBuilder jwtBuilder = Jwts
                    .builder()
                    .signWith(SignatureAlgorithm.HS256,SALT.getBytes(StandardCharsets.UTF_8))
                    .setClaims(claims);

            String verify = jwtBuilder.compact();
            if(verify.equals(token)){
                result.put("res","验证通过！");
                result.put("valid",true);
            }else{
                result.put("res","token 对应不上!");
                result.put("valid",false);
            }
        } catch (ExpiredJwtException e) {
            result.put("res","令牌过期！");
            result.put("valid",false);
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            result.put("res","令牌格式畸形！");
            result.put("valid",false);
            e.printStackTrace();
        } catch (SignatureException e) {
            result.put("res","密钥错误");
            result.put("valid",false);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            result.put("res","负载参数不合法");
            result.put("valid",false);
            e.printStackTrace();
        }catch (Exception e){
            result.put("res","其它异常");
            result.put("valid",false);
            e.printStackTrace();
        }
        return result;
    }
}
