package com.playground.filter;

import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.playground.constants.MessageCode;
import com.playground.constants.PlaygroundConstants;
import com.playground.exception.BizException;
import com.playground.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  
  private final JwtTokenUtil jwtTokenUtil;
  
  private final RedisTemplate redisTemplate;
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String userId = "";
    String url = request.getRequestURI();
    
    
      
    // 1. Request Header 에서 JWT 토큰 추출
    String token = jwtTokenUtil.resolveToken(request);
    
    // 2. validateToken 으로 토큰 유효성 검사
    if (url.matches("^/\\w+/api/.*") && token != null) {
      if(jwtTokenUtil.validateToken(token)) {
        // (추가) Redis 에 해당 accessToken logout 여부 확인
        String isLogout = (String)redisTemplate.opsForValue().get(token);
        if (ObjectUtils.isEmpty(isLogout)) {  
          // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
          Authentication authentication = jwtTokenUtil.getAuthentication(token);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          userId = jwtTokenUtil.getUserIdFromToken(token);
        }
      }
    }
    
    try {
      MDC.put("mberId", userId);
      filterChain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }

  public Claims parseJwtToken(String authorizationHeader) {
    validationAuthorizationHeader(authorizationHeader);
    String token = extractToken(authorizationHeader);

    try {
      return Jwts.parser().verifyWith(JwtTokenUtil.getKey()).build().parseSignedClaims(token).getPayload();
    } catch (Exception e) {
      throw new BizException(MessageCode.INVALID_TOKEN);
    }
  }

  private void validationAuthorizationHeader(String header) {
    if (header == null || !header.startsWith(PlaygroundConstants.TOKEN_PREFIX)) {
      throw new BizException(MessageCode.INVALID_TOKEN);
    }
  }

  private String extractToken(String authorizationHeader) {
    return authorizationHeader.substring(PlaygroundConstants.TOKEN_PREFIX.length());
  }

}
