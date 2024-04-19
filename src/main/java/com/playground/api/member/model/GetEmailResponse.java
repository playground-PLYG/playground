package com.playground.api.member.model;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "GetEmailResponse", description = "회원 여부 조회")
@Getter
@Setter
public class GetEmailResponse extends BaseDto {

  @Schema(description = "회원ID")
  private String mberId;
	
  @Schema(description = "회원명")
  private String mberNm;
  
  @Schema(description = "회원생년월일")
  private String mberBymd;
  
  @Schema(description = "회원성별코드")
  private String mbrSexdstnCode;

  @Schema(description = "회원이메일주소")
  private String mbrEmailAdres;
  
  @Schema(description = "ci내용")
  private String ciCn;
  
  @Schema(description = "di내용")
  private String diCn;
  
  @Schema(description = "회원전화번호")
  private String mberTelno;
  
}
