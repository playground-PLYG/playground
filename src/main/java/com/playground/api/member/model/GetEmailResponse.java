package com.playground.api.member.model;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "GetEmailResponse", description = "회원 여부 조회")
@Getter
@Setter
public class GetEmailResponse extends BaseDto {

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
  
  @Schema(description = "ci값")
  private String ciVl;
  
  @Schema(description = "di값")
  private String diVl;
  
  @Schema(description = "회원전화번호")
  private String mbrTelno;
  
}
