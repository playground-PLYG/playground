package com.playground.api.member.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "LoginResponse", description = "로그인 응답 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class SignInResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "토큰", example = "JWT 토큰")
  private final String token;

  @Builder
  public SignInResponse(String token) {
    super();
    this.token = token;
  }
}
