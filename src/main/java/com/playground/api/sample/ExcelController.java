package com.playground.api.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.sample.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "excel", description = "Excel 샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground/public/sample/excel")
public class ExcelController {
  private final ExcelService excelService;

  /**
   * Excel download
   */
  @Operation(summary = "Excel download", description = "Excel 다운로드 샘플")
  @GetMapping("/download")
  public ResponseEntity<byte[]> download() {
    return excelService.download();
  }

  /**
   * Excel download With Style
   */
  @Operation(summary = "Excel download With Style", description = "Cell에 스타일이 적용된 Excel 다운로드 샘플")
  @GetMapping("/download-style")
  public ResponseEntity<byte[]> downloadWithStyle() {
    return excelService.downloadWithStyle();
  }
}



