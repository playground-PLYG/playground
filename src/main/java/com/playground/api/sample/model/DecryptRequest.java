package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "DecryptRequest", description = "복호화 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class DecryptRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "복호화 할 문자열은 필수 값 입니다.")
  @Schema(description = "복호화 할 문자열", requiredMode = RequiredMode.REQUIRED,
      example = "4+uZCvlFjkikD3+E3ESZF2uYpf3/ZpcOEd2pksSvvFr3VtzFU780JNyWT7rcHHRj")
  private String encryptedText;
}
