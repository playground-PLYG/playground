package com.playground.filter;

import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.constants.MessageCode;
import com.playground.constants.PlaygroundConstants;
import com.playground.entity.BaseEntity;
import com.playground.exception.BizException;
import com.playground.model.BaseResponse;
import com.playground.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
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
    
    try {
      
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
      MDC.put("mberId", userId);
      filterChain.doFilter(request, response);
      
    }catch(UnsupportedJwtException e) { // 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
      jwtExceptionHandler(response,new BizException(MessageCode.UNSUPPROTED_TOKEN, MessageCode.UNSUPPROTED_TOKEN.getMessage()));
    }catch(MalformedJwtException e) { // JWT가 올바르게 구성되지 않았을 때
      jwtExceptionHandler(response,new BizException(MessageCode.INVALID_TOKEN, MessageCode.INVALID_TOKEN.getMessage()));
    }catch(ExpiredJwtException e) { // JWT를 생성할 때 지정한 유효기간 초과할 때.
      jwtExceptionHandler(response,new BizException(MessageCode.EXPIRED_TOKEN, MessageCode.EXPIRED_TOKEN.getMessage()));
    }catch(IllegalArgumentException e) { // JWT의 claim이 비어있는 경우
      jwtExceptionHandler(response,new BizException(MessageCode.WRONG_TOKEN, MessageCode.WRONG_TOKEN.getMessage()));
    }catch(SignatureException e) { // JWT의 기존 서명을 확인하지 못했을 때
      jwtExceptionHandler(response,new BizException(MessageCode.NOT_SIGNATRUE_TOKEN, MessageCode.NOT_SIGNATRUE_TOKEN.getMessage()));
    } finally {
      MDC.clear();
    }
  }
  
  //토큰에 대한 오류가 발생했을 때, 커스터마이징해서 Exception 처리 값을 클라이언트에게 알려준다.
  public void jwtExceptionHandler(HttpServletResponse response, BizException err) {
     response.setStatus(HttpStatus.UNAUTHORIZED.value());
     response.setContentType("application/json");
     response.setCharacterEncoding("UTF-8");
     try {
         String json = new ObjectMapper().writeValueAsString(new BaseResponse<>(err));
         response.getWriter().write(json);
     } catch (Exception e) {
         log.error(e.getMessage());
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
