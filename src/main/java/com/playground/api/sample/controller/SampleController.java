package com.playground.api.sample.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.sample.model.SampleUserResponse;
import com.playground.api.sample.model.SampleUserSearchRequest;
import com.playground.api.sample.service.SampleService;
import com.playground.constants.MessageCode;
import com.playground.exception.BizException;
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
   * 샘플 회원 목록 조회
   */
  @Operation(summary = "샘플 회원 목록 조회", description = "샘플 회원 목록 조회")
  @PostMapping("/public/sample/exception")
  public int test(@RequestBody @Valid SampleUserSearchRequest req, Pageable pageable) {
    int i = 0;
    int j = 1;

    return j / i;
  }

  /**
   * 샘플 회원 목록 조회
   */
  @PostMapping("/public/sample/exception1")
  public int exception1() {
    int i = 1;

    if (i > 0) {
      throw new BizException("asd");
    }

    return i;
  }

  @PostMapping("/public/sample/exception2")
  public int exception2() {
    int i = 1;

    if (i > 0) {
      throw new BizException(MessageCode.ACCESS_NOT_USER);
    }

    return i;
  }

  @PostMapping("/public/sample/exception3")
  public int exception3() {
    int i = 1;

    if (i > 0) {
      throw new BizException(MessageCode.CONFIRM);
    }

    return i;
  }

  @PostMapping("/public/sample/exception4")
  public int exception4() {
    int i = 1;

    if (i > 0) {
      throw new BizException(MessageCode.CONFIRM, "asdf");
    }

    return i;
  }

  @PostMapping("/public/sample/exception5")
  public int exception5() {
    int i = 1;

    if (i > 0) {
      throw new BizException(MessageCode.CONFIRM, new String[] {"asdf"});
    }

    return i;
  }

}


