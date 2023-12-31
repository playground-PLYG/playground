package com.playground.api.member.model;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "MemberRequest", description = "요청 데이터")
@Getter
@Setter
public class MemberSearchRequest  extends BaseDto {
	
	 @Schema(description = "회원번호")
	 private Integer mbrNo;
	 
	 @Schema(description = "회원명")
	 private String mbrNm;
	 
	 @Schema(description = "회원생년월일")
	 private String mbrBrdt;
	 
	 @Schema(description = "회원성별코드")
	 private String mbrGndrCd;
	 
	 @Schema(description = "회원이메일주소")
	 private String mbrEmlAddr;
	 
	 @Schema(description = "회원전화번호ID")
	 private String MbrTelno;

}
