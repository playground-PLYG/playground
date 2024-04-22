package com.playground.filter;

import java.io.IOException;
import java.util.List;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.common.net.HttpHeaders;
import com.playground.constants.MessageCode;
import com.playground.constants.PlaygroundConstants;
import com.playground.exception.BizException;
import com.playground.model.LoginMemberDto;
import com.playground.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String userName;
    String token;
    String userId = "";

    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    String url = request.getRequestURI();

    if (url.matches("^/\\w+/api/.*") && authorizationHeader != null && authorizationHeader.startsWith(PlaygroundConstants.TOKEN_PREFIX)) { // Bearer 토큰 파싱
      token = authorizationHeader.substring(7); // jwt token 파싱
      userName = JwtTokenUtil.getUsernameFromToken(token); // username 얻어오기
      userId = JwtTokenUtil.getUserIdFromToken(token);

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

      parseJwtToken(authorizationHeader);
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
