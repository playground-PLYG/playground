package com.playground.api.member.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "SignInResponse", description = "로그인 응답 데이터")
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
public class SignInResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;
  
  @Schema(description = "회원ID", example = "abc12")
  private String mberId;
  
  @Schema(description = "access 토큰", example = "JWT 토큰")
  private String accessToken;
  
  @Schema(description = "refresh 토큰", example = "JWT 토큰")
  private String refreshToken;
  
  @Schema(description = "refresh 토큰 유효시간")
  private Long refreshTokenExpirationTime;
  
  @Schema(description = "권한 타입")
  private String grantType;
}
