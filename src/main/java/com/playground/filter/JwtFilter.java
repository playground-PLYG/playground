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
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
    /*
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      request.setAttribute("exception", MessageCode.INVALID_TOKEN.getCode());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    } catch (ExpiredJwtException e) {
      request.setAttribute("exception", MessageCode.EXPIRED_TOKEN.getCode());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    } catch (UnsupportedJwtException e) {
      request.setAttribute("exception", MessageCode.UNSUPPROTED_TOKEN.getCode());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    } catch (IllegalArgumentException e) {
      request.setAttribute("exception", MessageCode.WRONG_TOKEN.getCode());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    */
    } catch(Exception e) {
      request.setAttribute("exception", MessageCode.UNKNOWN.getCode());
    } finally {
      MDC.clear();
    }
    
    MDC.put("mberId", userId);
    filterChain.doFilter(request, response);
    
    /*
    String userName;
    
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String url = request.getRequestURI();

    if (url.matches("^/\\w+/api/.*") && authorizationHeader != null && authorizationHeader.startsWith(PlaygroundConstants.TOKEN_PREFIX)) { // Bearer 토큰 파싱
      token = authorizationHeader.substring(7); // jwt token 파싱
      userName = JwtTokenUtil.getUsernameFromToken(token); // username 얻어오기
      

      // 현재 SecurityContextHolder 에 인증객체가 있는지 확인
      if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        // 토큰 유효여부 확인
        log.debug(">>> JWT Filter token = {}", token);
        if (Boolean.TRUE.equals(JwtTokenUtil.isValidToken(token))) {
          LoginMemberDto userDto = LoginMemberDto.builder().mberId(userId).mberNm(userName).build();

          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
              new UsernamePasswordAuthenticationToken(userDto, null, List.of(new SimpleGrantedAuthority("USER")));

          usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
    }
    */

    try {
      
      
    } finally {
      
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
