package com.playground.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.playground.filter.JwtFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // Spring Security filter를 filterchain에 등록
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).headers(headers -> {
      headers.xssProtection(Customizer.withDefaults());
      headers.frameOptions(FrameOptionsConfig::sameOrigin);
    }).sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X
        .formLogin(AbstractHttpConfigurer::disable) // 화면 없으니 설정
        .httpBasic(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll()) // 위 3개의 url을 제외한 나머지는 다 접근 가능 설정
        .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();

  }

}
