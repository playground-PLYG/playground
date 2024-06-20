package com.playground.api.member.model;

import java.io.Serial;
import com.playground.annotation.Secret;
import com.playground.api.restaurant.model.RstrntDetailSrchResponse;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "MberInfoResponse", description = "내 정보 조회 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
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
  
  @Schema(description = "회원생년월일")
  private String mberBymd;

  @Schema(description = "회원성별코드")
  private String mberSexdstnCode;

  @Schema(description = "회원전화번호")
  private String mberTelno;
  
  @Schema(description = "권한ID")
  private String authorId;

  @Schema(description = "권한명")
  private String authorNm;
}
