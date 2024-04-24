package com.playground.api.sample.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.sample.model.SampleUserResponse;
import com.playground.api.sample.model.SampleUserSearchRequest;
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
  public Page<SampleUserResponse> getSampleUserPageList(@RequestBody @Valid SampleUserSearchRequest req, Pageable pageable) {
    return sampleService.getUserPageList(req, pageable);
  }

  /**
   * 샘플 목록 조회
   *
   * @return List<SmpleResponse> - 조회한 샘플 목록
   */
  @Operation(summary = "샘플 목록 조회", description = "샘플 테이블 정보를 전체 조회")
  @GetMapping("/public/sample/getSmpleList")
  public List<SmpleResponse> getSmpleList() {
    return sampleService.getSmpleList();
  }
}


