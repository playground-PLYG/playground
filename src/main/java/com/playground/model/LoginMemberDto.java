package com.playground.model;

import java.io.Serial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "LoginMemberDto", description = "로그인 회원 정보")
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginMemberDto extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "회원ID")
  private String mberId;

  @Schema(description = "회원명")
  private String mberNm;

  @Schema(description = "회원생년월일")
  private String mberBymd;

  @Schema(description = "회원성별코드")
  private String mberSexdstnCode;

  @Schema(description = "회원이메일주소")
  private String mberEmailAdres;

  @Schema(description = "회원전화번호")
  private String mberTelno;


}
