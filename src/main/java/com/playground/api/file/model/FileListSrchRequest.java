package com.playground.api.file.model;

import java.io.Serial;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "FileListSrchRequest", description = "파일 목록 조회 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class FileListSrchRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "파일ID는 필수 값 입니다.")
  @Schema(description = "파일ID", requiredMode = RequiredMode.REQUIRED)
  private List<Integer> fileIds;
}
