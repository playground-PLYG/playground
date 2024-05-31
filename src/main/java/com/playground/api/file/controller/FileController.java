package com.playground.api.file.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.file.model.FileRemoveRequest;
import com.playground.api.file.model.FileSaveRequest;
import com.playground.api.file.model.FileSaveResponse;
import com.playground.api.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "file", description = "파일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class FileController {

  private final FileService fileService;

  /**
   * 파일 업로드
   */
  @Operation(summary = "파일 업로드", description = "파일 업로드")
  @PostMapping("/public/file/saveFile")
  public FileSaveResponse saveFile(FileSaveRequest reqData) {
    return fileService.saveFile(reqData);
  }

  /**
   * 파일 다운로드
   */
  @Operation(summary = "파일 다운로드", description = "파일 다운로드")
  @GetMapping("/public/file/getFile")
  public ResponseEntity<byte[]> getFile(@RequestParam(required = true) Integer fileId) {
    return fileService.getFile(fileId);
  }

  /**
   * 이미지 조회
   */
  @Operation(summary = "이미지 조회", description = "이미지 조회")
  @GetMapping("/public/file/getImage")
  public ResponseEntity<byte[]> getImage(@RequestParam(required = true) Integer fileId) {
    return fileService.getImage(fileId);
  }

  /**
   * 파일 삭제
   */
  @Operation(summary = "파일 삭제", description = "파일 삭제")
  @PostMapping("/public/file/removeFile")
  public void removeFile(@RequestBody @Valid FileRemoveRequest reqData) {
    fileService.removeFile(reqData);
  }
}


