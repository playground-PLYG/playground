package com.playground.api.sample.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "SampleRequest", description = "샘플 요청 데이터")
@Getter
@Setter
public class SampleUserSearchRequest extends BaseDto {

  @Schema(description = "사용자ID", example = "hong12")
  private String mberId;

  @Schema(description = "이름", example = "홍길동")
  private String mberNm;

  @Schema(description = "이메일", example = "emailId@gmail.com")
  private String mberEmailAdres;
}
