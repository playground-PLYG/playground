package com.playground.api.sample.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "DecryptResponse", description = "복호화 응답 데이터")
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DecryptResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "복호화 요청 문자열", example = "4+uZCvlFjkikD3+E3ESZF2uYpf3/ZpcOEd2pksSvvFr3VtzFU780JNyWT7rcHHRj")
  private String inputStr;

  @Schema(description = "복호화 결과 문자열", example = "테스트문자열123!@#")
  private String resultStr;
}
