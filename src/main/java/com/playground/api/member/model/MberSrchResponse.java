package com.playground.api.member.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "MberSrchResponse", description = "조회 응답 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MberSrchResponse extends BaseDto {
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
