package com.playground.api.file.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "FileRemoveRequest", description = "파일 삭제 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class FileRemoveRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "파일ID는 필수 값 입니다.")
  @Schema(description = "파일ID", requiredMode = RequiredMode.REQUIRED)
  private Integer fileId;
}
