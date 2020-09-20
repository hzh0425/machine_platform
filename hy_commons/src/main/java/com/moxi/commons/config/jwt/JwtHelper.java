package com.moxi.commons.config.jwt;

import com.moxi.commons.config.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtHelper {
    public final static String issue="cloud.creatorsn.com";
    public final static String subject="AccessToken";
    /**
     * 解析jwt
     */
    public Claims parseJWT(String token, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * 解析某一个加密字段
     */
    public String parseAndGet(String token,String base64Security,String field){
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(token).getBody();
        return (String)claims.get(field);
    }

    /**
     * 构建jwt
     * @param adminUid       账户id
     * @param audience       代表这个Jwt的接受对象
     * @param TTLMillis      jwt有效时间
     * @param base64Security 加密方式
     * @return
     */
    public String createJWT(String adminUid,
                            String audience, long TTLMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("uid", adminUid)
                .setSubject(subject)
                .setIssuer(issue)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }

    // 判断token是否已过期
    public boolean isExpiration(String token, String base64Security) {
        if (parseJWT(token, base64Security) == null) {
            return true;
        } else {
            return parseJWT(token, base64Security).getExpiration().before(new Date());
        }
    }


    //效验token
    public Boolean validateToken(String token, UserDetails userDetails, String base64Security) {
        SecurityUser SecurityUser = (SecurityUser) userDetails;
        final String username = getUsername(token, base64Security);
        final boolean expiration = isExpiration(token, base64Security);
        return (
                username.equals(SecurityUser.getUsername())
                        && !expiration);
    }


    //从token中获取用户名
    public String getUsername(String token, String base64Security) {
        return parseJWT(token, base64Security).getSubject();
    }

    //从token中获取用户UID
    public String getUserUid(String token, String base64Security) {
        return parseJWT(token, base64Security).get("uid", String.class);
    }

    //从token中获取audience
    public String getAudience(String token, String base64Security) {
        return parseJWT(token, base64Security).getAudience();
    }

    //从token中获取issuer
    public String getIssuer(String token, String base64Security) {
        return parseJWT(token, base64Security).getIssuer();
    }

    //从token中获取issuer
    public Date getExpiration(String token, String base64Security) {
        return parseJWT(token, base64Security).getExpiration();
    }

    //token是否可以更新
    public Boolean canTokenBeRefreshed(String token, String base64Security) {
        return !isExpiration(token, base64Security);
    }

    // 更新token
    public String refreshToken(String token, String base64Security, long TTLMillis) {
        String refreshedToken;
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            // 生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            final Claims claims = parseJWT(token, base64Security);
            claims.put("creatDate", new Date());
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuer(issue)
                    .setAudience(getAudience(token, base64Security))
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp).setNotBefore(now);
            }
            refreshedToken = builder.compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

}
