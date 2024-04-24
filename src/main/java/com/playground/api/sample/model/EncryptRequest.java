package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "EncryptRequest", description = "암호화 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class EncryptRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "암호화 할 문자열은 필수 값 입니다.")
  @Schema(description = "암호화 할 문자열", requiredMode = RequiredMode.REQUIRED, example = "테스트문자열123!@#")
  private String plainText;

}
