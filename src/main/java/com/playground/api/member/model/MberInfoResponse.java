package com.playground.api.member.model;

import java.io.Serial;
import com.playground.annotation.Secret;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "MemberInfoResponse", description = "내 정보 조회 응답 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MberInfoResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "회원ID", example = "test1")
  private String mberId;

  @Secret
  @Schema(description = "회원비밀번호", example = "1234")
  private String mberPassword;

  @Schema(description = "회원명", example = "홍길동")
  private String mberNm;

  @Schema(description = "회원이메일주소", example = "emailId@gmail.com")
  private String mberEmailAdres;
}
