package com.member.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "MyInfoResponse", description= "내 정보 조회 응답 데이터")
@Data
public class MyInfoResponse {

	@Schema(description = "사용자ID", defaultValue = "", example = "hong12")
	private String userId; //아이디
	
	@Schema(description = "비밀번호", defaultValue = "", example = "1111")
	private String password; //패스워드
	
	@Schema(description = "이름", defaultValue = "", example = "홍길동")
	private String name; //이름
	
	@Schema(description = "주민등록번호", defaultValue = "", example = "900101-1234567")
	private String regNo; //주민등록번호
}
