package com.playground.api.sample.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.sample.entity.SmpleEntity;
import com.playground.api.sample.model.SampleUserResponse;
import com.playground.api.sample.model.SampleUserSearchRequest;
import com.playground.api.sample.model.SmpleDetailDetailRequest;
import com.playground.api.sample.model.SmpleDetailDetailResponse;
import com.playground.api.sample.model.SmpleDetailRequest;
import com.playground.api.sample.model.SmpleDetailResponse;
import com.playground.api.sample.model.SmpleRequest;
import com.playground.api.sample.model.SmpleResponse;
import com.playground.api.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "sample", description = "샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class SampleController {
  private final SampleService sampleService;

  /**
   * 샘플 회원 목록 조회
   */
  @Operation(summary = "샘플 회원 목록 조회", description = "샘플 회원 목록 조회")
  @PostMapping("/public/sample/users")
  public Page<SampleUserResponse> getSampleUserPageList(@RequestBody @Valid SampleUserSearchRequest reqData, Pageable pageable) {
    return sampleService.getUserPageList(reqData, pageable);
  }

  /**
   * 샘플 목록 조회
   *
   * @return List<SmpleResponse> - 조회한 샘플 목록
   */
  @Operation(summary = "샘플 목록 조회", description = "샘플 테이블 정보 전체 조회")
  @GetMapping("/public/sample/getSmpleList")
  public List<SmpleResponse> getSmpleList() {
    return sampleService.getSmpleList();
  }

  /**
   * 샘플 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleResponse - 조회한 샘플 단건
   */
  @Operation(summary = "샘플 단건 조회", description = "샘플 테이블 정보 단건 조회")
  @PostMapping("/public/sample/getSmpleDetail")
  public SmpleResponse getSmpleDetail(@RequestBody @Valid SmpleRequest reqData) {
    return sampleService.getSmpleDetail(reqData);
  }

  /**
   * 샘플 상세 목록 조회
   *
   * @param reqData - 조회 조건
   * @return List<SmpleDetailResponse> - 조회한 샘플 상세 목록
   */
  @Operation(summary = "샘플 상세 목록 조회", description = "샘플 상세 테이블 정보 목록 조회")
  @PostMapping("/public/sample/getSmpleDetailList")
  public List<SmpleDetailResponse> getSmpleDetailList(@RequestBody @Valid SmpleDetailRequest reqData) {
    return sampleService.getSmpleDetailList(reqData);
  }

  /**
   * 샘플 상세 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleDetailResponse - 조회한 샘플 상세 단건
   */
  @Operation(summary = "샘플 상세 단건 조회", description = "샘플 상세 테이블 정보 단건 조회")
  @PostMapping("/public/sample/getSmpleDetailDetail")
  public SmpleDetailResponse getSmpleDetailDetail(@RequestBody @Valid SmpleDetailRequest reqData) {
    return sampleService.getSmpleDetailDetail(reqData);
  }

  /**
   * 샘플 상세 상세 목록 조회
   *
   * @param reqData - 조회 조건
   * @return List<SmpleDetailDetailResponse> - 조회한 샘플 상세 상세 목록
   */
  @Operation(summary = "샘플 상세 상세 목록 조회", description = "샘플 상세 상세 테이블 정보 목록 조회")
  @PostMapping("/public/sample/getSmpleDetailDetailList")
  public List<SmpleDetailDetailResponse> getSmpleDetailDetailList(@RequestBody @Valid SmpleDetailDetailRequest reqData) {
    return sampleService.getSmpleDetailDetailList(reqData);
  }

  /**
   * 샘플 상세 상세 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleDetailDetailResponse - 조회한 샘플 상세 상세 단건
   */
  @Operation(summary = "샘플 상세 상세 단건 조회", description = "샘플 상세 상세 테이블 정보 단건 조회")
  @PostMapping("/public/sample/getSmpleDetailDetailDetail")
  public SmpleDetailDetailResponse getSmpleDetailDetailDetail(@RequestBody @Valid SmpleDetailDetailRequest reqData) {
    return sampleService.getSmpleDetailDetailDetail(reqData);
  }
  
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 단건
   */
  @Operation(summary = "샘플 QueryDsl 단건 조회", description = "샘플 테이블 정보를 단건 조회")
  @PostMapping("/public/sample/getSmpleDsl")
  public SmpleResponse getSmpleDsl(@RequestBody SmpleRequest req) {
    return sampleService.getSmpleDsl(req);
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 다건
   */
  @Operation(summary = "샘플 QueryDsl 다건 조회", description = "샘플 테이블 정보를 다건 조회")
  @PostMapping("/public/sample/getSmpleDslList")
  public List<SmpleResponse> getSmpleDslList(@RequestBody SmpleRequest req) {
    return sampleService.getSmpleDslList(req);
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 페이징
   */
  @Operation(summary = "샘플 QueryDsl 단건 조회", description = "샘플 테이블 정보를 단건 조회")
  @PostMapping("/public/sample/getSmpleDslPageList")
  public Page<SmpleResponse> getSmpleDslPageList(@RequestBody SmpleRequest req, Pageable pageable) {
    return sampleService.getSmpleDslPageList(req, pageable);
  }
  
}


