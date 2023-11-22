package com.playground.api.code;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

@Tag(name = "code", description = "공통코드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class CodeController {

  private final CodeService codeService;
  private final Logger LOGGER = LoggerFactory.getLogger(CodeController.class.getName());
  
  /**
   * 코드 조회
   */
  @Operation(summary = "코드 조회", description = "코드 조회")
  @PostMapping("/public/code/codeSearch")
  public ResponseEntity<BaseResponse<Page<CodeResponse>>> getCodeList(@RequestBody @Valid CodeSearchRequest req,
      Pageable pageable) {
	  
	  LOGGER.debug("CodeSearchRequest:::::::::::::::"+req);

    return ResponseEntity.ok(new BaseResponse<>(codeService.getCodePageList(req, pageable)));
  }
  
  
  /**
   * 코드 삭제
   */
  @Operation(summary = "코드 삭제", description = "코드 삭제")
  @PostMapping("/public/code/codeDelete")
  public void deleteCode(@RequestBody @Valid CodeSearchRequest req) {
	  
	  LOGGER.debug("codeDelete:::::::::::::::"+req);
	  
	  codeService.deleteCode(req);
  }
  
  /**
   * 코드 등록
   */
  @Operation(summary = "코드 등록", description = "코드 등록")
  @PostMapping("/public/code/codeSave")
  public ResponseEntity<BaseResponse<CodeResponse>> saveCodeList(@RequestBody @Valid CodeSearchRequest req) {
	  
	  LOGGER.debug("saveCodeList:::::::::::::::"+req);
	  	
	  	return ResponseEntity.ok(new BaseResponse<>(codeService.saveCodeList(req)));	
  }
  
  /**
   * 코드 수정
   */
  @Operation(summary = "코드 수정", description = "코드 수정")
  @PostMapping("/public/code/codeUpdate")
  public ResponseEntity<BaseResponse<CodeResponse>> updateCodeList(@RequestBody @Valid CodeSearchRequest req) {
	  
	  LOGGER.debug("updateCodeList:::::::::::::::"+req);
	  	
	  	return ResponseEntity.ok(new BaseResponse<>(codeService.updateCodeList(req)));	
  }

}



