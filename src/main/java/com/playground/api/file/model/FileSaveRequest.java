package com.playground.api.file.model;

import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "FileSaveRequest", description = "파일 업로드 요청 데이터")
@Builder
@AllArgsConstructor
@Getter
public class FileSaveRequest {
  @Schema(description = "파일", requiredMode = RequiredMode.REQUIRED)
  private MultipartFile file;

  private String type;
}
