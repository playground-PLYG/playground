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

  @Schema(description = "회원ID")
  private Integer mberNo;
  
  @Schema(description = "회원명")
  private String mberNm;
  
  @Schema(description = "회원생년월일")
  private String mberBymd;
  
  @Schema(description = "회원성별코드")
  private String mberSexdstnCode;
  
  @Schema(description = "회원이메일주소")
  private String mberEmailAdres;
  
  @Schema(description = "회원전화번호")
  private String MberTelno;
  
  @Schema(description = "등록사용자ID")
  private String registUsrId;

  @Schema(description = "등록일시")
  private LocalDateTime registDt;

  @Schema(description = "수정사용자ID")
  private String updtUsrId;

  @Schema(description = "수정일시")
  private LocalDateTime updtDt;
  
}
