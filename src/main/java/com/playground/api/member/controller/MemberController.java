package com.playground.api.member.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.member.model.GetEmailResponse;
import com.playground.api.member.model.MemberInfoResponse;
import com.playground.api.member.model.MemberResponse;
import com.playground.api.member.model.MemberSearchRequest;
import com.playground.api.member.model.PgSignUpRequest;
import com.playground.api.member.model.PgSignUpResponse;
import com.playground.api.member.model.SignInRequest;
import com.playground.api.member.model.SignInResponse;
import com.playground.api.member.model.SignUpRequest;
import com.playground.api.member.model.SignUpResponse;
import com.playground.api.member.service.MemberService;
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
public class MemberController {

  private final MemberService memberService;

  /**
   * 회원가입
   */
  @Operation(summary = "회원가입", description = "회원 가입하기")
  @PostMapping("/public/member/sign-up")
  public SignUpResponse signUp(@RequestBody @Valid SignUpRequest req) {
    return memberService.signUp(req);
  }

  /**
   * 로그인
   */
  @Operation(summary = "인증", description = "인증 처리")
  @PostMapping("/public/member/sign-in")
  public SignInResponse signIn(@RequestBody @Valid SignInRequest req) {
    return memberService.signIn(req);
  }

  /**
   * 내 정보 조회
   */
  @Operation(summary = "내 정보 조회", description = "본인의 정보를 조회")
  @GetMapping("/api/member/me")
  public MemberInfoResponse myInfo(@RequestHeader(value = "Authorization") String token) {
    return memberService.myInfo(token);
  }

  /**
   * 회원 여부 조회
   */
  @Operation(summary = "회원 여부 조회", description = "회원 여부 조회")
  @GetMapping("/public/pgMember/get-email")
  public GetEmailResponse getEmail(@RequestParam String email) {
    return memberService.getEmail(email);
  }

  /**
   * 회원 가입
   */
  @Operation(summary = "회원 가입", description = "회원 등록")
  @PostMapping("/public/pgMember/sign-up")
  public PgSignUpResponse signUp(@RequestBody @Valid PgSignUpRequest req) {
    return memberService.pgSignUp(req);
  }

  /**
   * 회원 조회
   */
  @Operation(summary = "회원 조회", description = "회원 조회")
  @PostMapping("/public/pgMember/memberSearch")
  public List<MemberResponse> getMemberList(@RequestBody @Valid MemberSearchRequest req) {

    log.debug(">>> 회원조회 : {}", req);

    return memberService.getMemeberList(req);
  }

}

