package com.playground.api.member.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "MemberResponse", description = "응답 데이터")
@Getter
@Setter
public class MemberResponse {
	
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
