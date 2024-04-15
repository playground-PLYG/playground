package com.playground.api.code;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.code.model.CodeResponse;
import com.playground.api.code.model.CodeSearchRequest;
import com.playground.api.code.service.CodeService;
import com.playground.model.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "code", description = "공통코드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class CodeController {

  private final CodeService codeService;

  /**
   * 코드 조회
   */
  @Operation(summary = "코드 조회", description = "코드 조회")
  @PostMapping("/public/code/codeSearch")
  public ResponseEntity<BaseResponse<List<CodeResponse>>> getCodeList(@RequestBody @Valid CodeSearchRequest req) {

    log.debug("CodeSearchRequest::::::::::::::: {}", req);

    return ResponseEntity.ok(new BaseResponse<>(codeService.getCodePageList(req)));
  }

  /**
   * 상위 조회
   */
  @Operation(summary = "상위코드id조회", description = "상위코드id 조회")
  @GetMapping("/public/code/selectUpCodeid")
  public ResponseEntity<BaseResponse<List<CodeResponse>>> selectUpCodeid() {
    return ResponseEntity.ok(new BaseResponse<>(codeService.selectUpCodeid()));
  }

  /**
   * 코드 삭제
   */
  @Operation(summary = "코드 삭제", description = "코드 삭제")
  @PostMapping("/public/code/codeDelete")
  public void deleteCode(@RequestBody @Valid CodeSearchRequest req) {

    log.debug("codeDelete::::::::::::::: {}", req);

    codeService.deleteCode(req);
  }

  /**
   * 코드 등록/수정
   */
  @Operation(summary = "코드 등록", description = "코드 등록")
  @PostMapping("/public/code/codeSave")
  public ResponseEntity<BaseResponse<CodeResponse>> saveCodeList(@RequestBody @Valid CodeSearchRequest req) {

    log.debug("saveCodeList::::::::::::::: {}", req);

    return ResponseEntity.ok(new BaseResponse<>(codeService.saveCodeList(req)));
  }



}


