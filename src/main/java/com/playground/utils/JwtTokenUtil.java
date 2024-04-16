package com.playground.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.util.StringUtils;
import com.playground.api.member.model.MemberInfoResponse;
import com.playground.constants.PlaygroundConstants;
import com.playground.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class JwtTokenUtil {
  private static final String USER_ID = "userId";
  
  private static final String secretKey = "PlaygroundTestKey256SecreyKeyTestKeyYamlfhQodigka256e39djf"; // 이거 좋은 방법 없나 확인 필요

  private static final SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

  // 토큰 생성
  public static String createToken(String userId, String name) {

    Map<String, Object> payloads = new HashMap<>();

    // API 용도에 맞게 properties로 관리하여 사용하는것을 권장한다.
    payloads.put(USER_ID, userId);
    payloads.put("name", name);

    // 토큰 유효 시간 (30분)
    long expiredTime = 1000 * 60 * 30L;

    Date ext = new Date(); // 토큰 만료 시간
    ext.setTime(ext.getTime() + expiredTime);

    return Jwts.builder()
        .claims(payloads) // Claims 설정
        .issuer("issuer") // 발급자
        .subject("auth") // 토큰 용도
        .expiration(ext) // 토큰 만료 시간 설정
        .signWith(key)
        .compact(); // 토큰 생성
  }

  /**
   * 토큰 만료여부 확인
   */
  public static Boolean isValidToken(String token) {
    return !isTokenExpired(token);
  }

  /**
   * 토큰의 Claim 디코딩
   */
  private static Claims getAllClaims(String token) {
    try {
      return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    } catch (Exception e) {
      throw new CustomException(MessageUtils.NOT_VERIFICATION_TOKEN);
    }
  }

  /**
   * Claim 에서 username 가져오기
   */
  public static String getUsernameFromToken(String token) {
    try {
      return String.valueOf(getAllClaims(token).get("name"));
    } catch (NullPointerException e) {
      throw new CustomException(MessageUtils.NOT_VERIFICATION_TOKEN);
    }
  }

  /**
   * Claim 에서 user_id 가져오기
   */
  public static String getUserIdFromToken(String token) {
    return String.valueOf(getAllClaims(token).get(USER_ID));
  }

  /**
   * 토큰 만료기한 가져오기
   */
  public static Date getExpirationDate(String token) {
    Claims claims = getAllClaims(token);
    return claims.getExpiration();
  }

  /**
   * 토큰이 만료되었는지
   */
  private static boolean isTokenExpired(String token) {
    return getExpirationDate(token).before(new Date());
  }

  public static MemberInfoResponse autholriztionCheckUser(String token) {
    String authorization = token;
    MemberInfoResponse rs = new MemberInfoResponse();

    if (StringUtils.hasText(token) && token.startsWith(PlaygroundConstants.TOKEN_PREFIX)) {
      authorization = authorization.replaceAll(PlaygroundConstants.TOKEN_PREFIX, "");

      log.debug(">>> authorization : {}", authorization);

      Claims claims = getAllClaims(authorization);

      rs.setName((String) claims.get("name"));
      rs.setUserId((String) claims.get(USER_ID));

      return rs;
    }

    return rs;
  }

  public static SecretKey getKey() {
    return key;
  }
}
