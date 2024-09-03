package com.playground.api.code.controller;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.code.model.CodeAddRequest;
import com.playground.api.code.model.CodeGroupSrchRequest;
import com.playground.api.code.model.CodeRemoveRequest;
import com.playground.api.code.model.CodeResponse;
import com.playground.api.code.model.CodeSearchRequest;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.service.CodeService;
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
  @PostMapping("/public/code/getCodePageList")
  public Page<CodeResponse> getCodePageList(Pageable pageable, @RequestBody @Valid CodeSearchRequest reqData) {
    return codeService.getCodePageList(pageable, reqData);

  }

  /**
   * 전체 코드 조회
   */
  @Operation(summary = "전체 코드 조회", description = "전체 코드 조회")
  @GetMapping("/public/code/getAllCodeList")
  public List<CodeResponse> getAllCodeList() {
    return codeService.getAllCodeList();
  }

  /**
   * 코드 삭제
   */
  @Operation(summary = "코드 삭제", description = "코드 삭제")
  @PostMapping("/public/code/removeCode")
  public void removeCode(@RequestBody @Valid List<CodeRemoveRequest> reqData) {
    codeService.removeCode(reqData);
  }

  /**
   * 코드 등록/수정
   */
  @Operation(summary = "코드 등록", description = "코드 등록")
  @PostMapping("/public/code/addCode")
  public CodeResponse addCode(@RequestBody @Valid CodeAddRequest reqData) {
    return codeService.addCode(reqData);
  }

  /**
   * 코드 조회
   */
  @Operation(summary = "코드 조회", description = "코드 조회")
  @PostMapping("/public/code/getCode")
  public CodeSrchResponse getCode(@RequestBody @Valid CodeSrchRequest reqData) {
    return codeService.getCode(reqData);
  }

  /**
   * 코드 그룹 조회
   */
  @Operation(summary = "코드 그룹 조회", description = "상위 코드 기준 코드 목록 조회")
  @PostMapping("/public/code/getCodeGroupList")
  public List<CodeSrchResponse> getCodeGroupList(@RequestBody @Valid CodeGroupSrchRequest reqData) {
    return codeService.getCodeGroupList(reqData);
  }
}
