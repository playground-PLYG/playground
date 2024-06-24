package com.playground.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.google.common.net.HttpHeaders;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.model.MberInfoResponse.MberInfoResponseBuilder;
import com.playground.api.member.model.SignInResponse;
import com.playground.constants.MessageCode;
import com.playground.constants.PlaygroundConstants;
import com.playground.exception.BizException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenUtil {
  private static final String USER_ID = "mberId";

  private static final String USER_NM = "mberNm";

  private static final String SECRET_KEY = "PlaygroundTestKey256SecreyKeyTestKeyYamlfhQodigka256e39djf"; // 이거 좋은 방법 없나 확인 필요

  private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

  private static final String AUTHORITIES_KEY = "auth";

  private static final String BEARER_TYPE = "Bearer";

  //private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L; // 30분
  
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1 * 60 * 1000L; // 1분 테스트 용도

  private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일

  // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
  public SignInResponse generateToken(Authentication authentication, String userNm) {
    // 권한 가져오기
    String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

    Map<String, Object> payloads = new HashMap<>();
    
    payloads.put(USER_NM, userNm);
    payloads.put(USER_ID, authentication.getName());
    payloads.put(AUTHORITIES_KEY, authorities);
    
    long now = (new Date()).getTime();
    // Access Token 생성
    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    
    String accessToken = Jwts.builder()
        .subject(authentication.getName())
        .claims(payloads)
        .expiration(accessTokenExpiresIn)
        .signWith(key)
        .compact();

    // Refresh Token 생성
    String refreshToken = Jwts.builder()
        .expiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
        .signWith(key)
        .compact();

    return SignInResponse.builder().grantType(BEARER_TYPE).accessToken(accessToken).refreshToken(refreshToken)
        .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME).build();
  }

  // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
  public Authentication getAuthentication(String accessToken) {
    // 토큰 복호화
    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    }

    // 클레임에서 권한 정보 가져오기
    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    // UserDetails 객체를 만들어서 Authentication 리턴
    UserDetails principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  // 토큰 정보를 검증하는 메서드
  public boolean validateToken(String token) {
    Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    return true;
  }

  private Claims parseClaims(String accessToken) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
  }

  public Long getExpiration(String accessToken) {
    // accessToken 남은 유효시간
    Date expiration = Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload().getExpiration();
    // 현재 시간
    Long now = new Date().getTime();
    return (expiration.getTime() - now);
  }

  /**
   * 토큰의 Claim 디코딩
   */
  private Claims getAllClaims(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Claim 에서 username 가져오기
   */
  public String getUsernameFromToken(String token) {
    return String.valueOf(getAllClaims(token).get(USER_NM));
  }

  /**
   * Claim 에서 user_id 가져오기
   */
  public String getUserIdFromToken(String token) {
    return String.valueOf(getAllClaims(token).get(USER_ID));
  }

  // Request의 Header에서 token 값을 가져옵니다. "authorization" : "token'
  public String resolveToken(HttpServletRequest request) {
    if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
      return request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
    }
    return null;
  }

  public MberInfoResponse autholriztionCheckUser(String token) {
    String authorization = token;
    MberInfoResponseBuilder mberInfoResponseBuilder = MberInfoResponse.builder();

    if (StringUtils.hasText(token) && token.startsWith(PlaygroundConstants.TOKEN_PREFIX)) {
      authorization = authorization.replaceAll(PlaygroundConstants.TOKEN_PREFIX, "");

      log.debug(">>> authorization : {}", authorization);

      Claims claims = getAllClaims(authorization);

      mberInfoResponseBuilder.mberId((String) claims.getSubject());

      return mberInfoResponseBuilder.build();
    }

    return mberInfoResponseBuilder.build();
  }

  public static SecretKey getKey() {
    return key;
  }
}
