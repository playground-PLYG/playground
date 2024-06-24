package com.playground.api.member.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "TokenRequest", description = "Token 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class TokenRequest extends BaseDto {

  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "accessToken 을 입력해주세요.")
  private String accessToken;

  @NotEmpty(message = "refreshToken 을 입력해주세요.")
  private String refreshToken;
}
