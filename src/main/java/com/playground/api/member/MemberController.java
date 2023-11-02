package com.playground.api.member;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.member.model.MemberInfoResponse;
import com.playground.api.member.model.SignInRequest;
import com.playground.api.member.model.SignInResponse;
import com.playground.api.member.model.SignUpRequest;
import com.playground.api.member.model.SignUpResponse;
import com.playground.api.member.service.MemberService;
import com.playground.model.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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
  public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@RequestBody @Valid SignUpRequest req) {
    return ResponseEntity.ok(new BaseResponse<>(memberService.signUp(req)));
  }

  /**
   * 로그인
   */
  @Operation(summary = "인증", description = "인증 처리")
  @PostMapping("/public/member/sign-in")
  public ResponseEntity<BaseResponse<SignInResponse>> signIn(@RequestBody @Valid SignInRequest req) {
    return ResponseEntity.ok(new BaseResponse<>(memberService.signIn(req)));
  }

  /**
   * 내 정보 조회
   */
  @Operation(summary = "내 정보 조회", description = "본인의 정보를 조회")
  @GetMapping("/api/member/me")
  public ResponseEntity<BaseResponse<MemberInfoResponse>> myInfo(@RequestHeader(value = "Authorization") String token) {
    return ResponseEntity.ok(new BaseResponse<>(memberService.myInfo(token)));
  }

}


