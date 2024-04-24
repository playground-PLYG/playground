package com.playground.api.member.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "SignResponse", description = "회원 가입 시 응답 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class SignUpResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "회원ID", example = "hong12")
  private String mberId;

  @Schema(description = "회원명", example = "홍길동")
  private String mberNm;

  @Schema(description = "회원이메일주소", example = "emailId@gmail.com")
  private String mberEmailAdres;
}
