package com.member.api.member.model;

import com.member.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "SignResponse", description = "회원 가입 시 응답 데이터")
@Getter
@Setter
public class SignUpResponse extends BaseDto {

  @Schema(description = "사용자ID", defaultValue = "", example = "hong12")
  private String userId;

  @Schema(description = "이름", defaultValue = "", example = "홍길동")
  private String name;

  @Schema(description = "이메일", defaultValue = "", example = "emailId@gmail.com")
  private String email;
}