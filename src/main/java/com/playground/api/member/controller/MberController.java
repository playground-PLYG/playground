package com.playground.api.member.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.api.member.model.MberSrchRequest;
import com.playground.api.member.model.MberSrchResponse;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.model.MberModifyInfoRequest;
import com.playground.api.member.model.SignInRequest;
import com.playground.api.member.model.SignInResponse;
import com.playground.api.member.model.SignUpRequest;
import com.playground.api.member.model.SignUpResponse;
import com.playground.api.member.service.MberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "member", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class MberController {

  private final MberService mberService;

  /**
   * 회원가입
   */
  @Operation(summary = "회원가입", description = "회원 가입하기")
  @PostMapping("/public/member/addMber")
  public SignUpResponse addMber(@RequestBody @Valid SignUpRequest req) {
    return mberService.addMber(req);
  }

  /**
   * 로그인
   */
  @Operation(summary = "인증", description = "인증 처리")
  @PostMapping("/public/member/signIn")
  public SignInResponse signIn(@RequestBody @Valid SignInRequest req) {
    return mberService.signIn(req);
  }

  /**
   * 로그아웃
   */
  @Operation(summary = "로그아웃", description = "로그아웃 처리")
  @PostMapping("/public/member/signOut")
  public void signOut(@RequestHeader(value = "Authorization") String token) {
    mberService.signOut(token);
  }

  /**
   * 내 정보 조회
   */
  @Operation(summary = "내 정보 조회", description = "본인의 정보를 조회")
  @GetMapping("/api/member/getMyInfo")
  public MberInfoResponse getMyInfo(@RequestHeader(value = "Authorization") String token) {
    return mberService.getMyInfo(token);
  }

  /**
   * 회원 조회
   */
  @Operation(summary = "회원 조회", description = "회원 조회")
  @PostMapping("/public/member/getMberPageList")
  public Page<MberSrchResponse> getMberPageList(Pageable pageable, @RequestBody @Valid MberSrchRequest req) {

    log.debug(">>> 회원조회 : {}", req);

    return mberService.getMberPageList(pageable, req);
  }

  /**
   * 페이징 포함 회원 조회
   */
  @Operation(summary = "회원 조회", description = "페이징 포함 회원 조회")
  @PostMapping("/public/member/getMberList")
  public List<MberSrchResponse> getMberList(@RequestBody @Valid MberSrchRequest req) {
    return mberService.getMberList(req);
  }

  /**
   * 회원 중복 조회
   */
  @Operation(summary = "회원 중복 조회", description = "회원 중복 조회")
  @PostMapping("/public/member/getMberDupCeck")
  public String getMberDupCeck(@RequestBody @Valid MberSrchRequest req) {

    log.debug(">>> 회원조회 : {}", req);

    return mberService.getMberDupCeck(req);
  }

  /**
   * 회원 정보 수정
   */
  @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 ")
  @PostMapping("/api/member/modifyMberinfo")
  public void getMberDupCeck(@RequestBody @Valid MberModifyInfoRequest req) {
    mberService.modifyMberinfo(req);
  }

}
