package com.member.api.member.model;

import com.member.annotation.Secret;
import com.member.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "LoginRequest", description= "로그인 요청 데이터")
@Getter
@Setter
public class LoginRequest extends BaseDto {

	@Schema(description = "사용자ID", defaultValue = "", example = "hong12")
	private String userId; //아이디

	@Secret
	@Schema(description = "비밀번호", defaultValue = "", example = "1111")
	private String password; //패스워드
}