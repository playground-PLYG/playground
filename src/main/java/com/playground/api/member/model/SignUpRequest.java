package com.playground.api.member.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.playground.annotation.Secret;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "SignRequest", description = "회원 가입 시 요청 데이터")
@Getter
@Setter
public class SignUpRequest extends BaseDto {

  @NotBlank(message = "ID는 필수 값 입니다.")
  @Schema(description = "회원ID", example = "hong12")
  private String mberId;

  @NotBlank(message = "비밀번호는 필수 값 입니다.")
  @Secret
  @Schema(description = "회원비밀번호", example = "1111")
  private String mberPassword;

  @NotBlank(message = "이름은 필수 값 입니다.")
  @Schema(description = "회원명", example = "홍길동")
  private String mberNm;

  @Email(message = "이메일 형식을 확인해 주세요.")
  @NotBlank(message = "이메일은 필수 값 입니다.")
  @Schema(description = "회원이메일주소", example = "emailId@gmail.com")
  private String mberEmailAdres;
}
