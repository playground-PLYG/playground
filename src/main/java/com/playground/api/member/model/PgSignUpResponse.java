package com.playground.api.member.model;

import java.time.LocalDateTime;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "PgSignUpResponse", description = "회원 가입 시 응답 데이터")
@Getter
@Setter
public class PgSignUpResponse extends BaseDto {

  @Schema(description = "회원번호")
  private int mbrNo;
	
  @Schema(description = "회원명")
  private String mbrNm;
  
  @Schema(description = "회원생년월일")
  private String mbrBrdt;
  
  @Schema(description = "회원성별코드")
  private String mbrGndrCd;

  @Schema(description = "회원이메일주소")
  private String mbrEmlAddr;
  
  @Schema(description = "회원전화번호")
  private String mbrTelno;
  
  @Schema(description = "등록회원번호")
  private int regMbrNo;

  @Schema(description = "등록일시")
  private LocalDateTime regDt;

  @Schema(description = "수정회원번호")
  private int mdfcnMbrNo;

  @Schema(description = "수정일시")
  private LocalDateTime mdfcnDt;
  
}
