package com.playground.api.sample.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.sample.model.DecryptRequest;
import com.playground.api.sample.model.DecryptResponse;
import com.playground.api.sample.model.EncryptRequest;
import com.playground.api.sample.model.EncryptResponse;
import com.playground.api.sample.model.PasswordCompareRequest;
import com.playground.api.sample.model.PasswordRequest;
import com.playground.api.sample.model.PasswordResponse;
import com.playground.utils.CryptoUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "crypto", description = "Crypto 샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground/public/sample/crypto")
public class CryptoController {
  /**
   * 암호화 - AES
   */
  @Operation(summary = "암호화 - AES", description = "AES 암호화")
  @PostMapping("/encrypt")
  public EncryptResponse encrypt(@RequestBody EncryptRequest request) {
    String input = request.getPlainText();

    return EncryptResponse.builder().inputStr(input).resultStr(CryptoUtil.encrypt(input)).build();
  }

  /**
   * 복호화 - AES
   */
  @Operation(summary = "복호화 - AES", description = "AES 복호화")
  @PostMapping("/decrypt")
  public DecryptResponse decrypt(@RequestBody DecryptRequest request) {
    String input = request.getEncryptedText();

    return DecryptResponse.builder().inputStr(input).resultStr(CryptoUtil.decrypt(input)).build();
  }

  /**
   * 비밀번호 단방향 암호화
   */
  @Operation(summary = "비밀번호 단방향 암호화", description = "비밀번호 단방향 암호화")
  @PostMapping("/password")
  public PasswordResponse password(@RequestBody PasswordRequest request) {
    String input = request.getPlainText();

    return PasswordResponse.builder().inputStr(input).resultStr(CryptoUtil.encodePassword(input)).build();
  }

  /**
   * 평문, 암호화 비밀번호 비교
   */
  @Operation(summary = "평문, 암호화 비밀번호 비교", description = "평문과 암호화된 비밀번호가 동일한 값인지 비교")
  @PostMapping("/password-compare")
  public Boolean passwordCompare(@RequestBody PasswordCompareRequest request) {
    return CryptoUtil.comparePassword(request.getPlainText(), request.getEncryptedText());
  }
}
