package com.ssafy.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
@PropertySource("classpath:/application.properties")
public class JWTUtil {

  private static String secretKey;
  public static long accessTokenExpireTime;
//  public static long refreshTokenExpireTime;
  
  public JWTUtil(
      @Value("${jwt.secret.key}")String secretKey, 
      @Value("${jwt.access-token.expiretime}")long accessTokenExpireTime) {
    super();
    JWTUtil.secretKey = secretKey;
    JWTUtil.accessTokenExpireTime = accessTokenExpireTime;
  }

  private String createToken(String uid, String type, long expireTime) {
    long currentTimeMillis = System.currentTimeMillis();
    Date issuedAt = new Date(currentTimeMillis);
    Date expiration = new Date(currentTimeMillis + expireTime);
    
    Map<String, Object> payload = new HashMap<>();
    payload.put("sub", "user_authentication");
    payload.put("exp", expiration);
    payload.put("iat", issuedAt);
    payload.put("tokenType", type);
    payload.put("uid", uid);
    
    Claims claims = Jwts.claims().add(payload).build();
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
    
    String jwt = Jwts.builder()
        .header()
          .add("typ", "JWT")
          .add("alg", "HS256")
          .and()
        .claims(claims)
        .signWith(key)
        .compact();
    
    return jwt;
  }
  
  public String createAccessToken(String uid) {
    return createToken(uid, "access-token", accessTokenExpireTime);
  }
  
//  public String createRefreshToken(String uid) {
//    return createToken(uid, "refresh-token", refreshTokenExpireTime);
//  }
  
  public Claims checkToken(String token) throws Exception {
      Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
      Jws<Claims> claims = Jwts.
          parser().
          verifyWith((SecretKey)key).
          build().
          parseSignedClaims(token);
      return claims.getPayload();
  }
}
