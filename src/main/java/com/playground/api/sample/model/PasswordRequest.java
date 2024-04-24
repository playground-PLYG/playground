package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "PasswordRequest", description = "암호화 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class PasswordRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "비밀번호는 필수 값 입니다.")
  @Schema(description = "암호화 할 비밀번호", requiredMode = RequiredMode.REQUIRED, example = "비밀번호1234!@#$")
  private String plainText;

}
