package com.playground.api.code.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "CodeSrchRequest", description = "코드 조회 요청 데이터")
@Builder
@EqualsAndHashCode(callSuper = true)
@Getter
public class CodeSrchRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "코드는 필수 값 입니다.")
  @Schema(description = "코드", requiredMode = RequiredMode.REQUIRED)
  private String code;

  @NotNull(message = "상위코드는 필수 값 입니다.")
  @Schema(description = "상위코드", requiredMode = RequiredMode.REQUIRED)
  private String upperCode;
}
