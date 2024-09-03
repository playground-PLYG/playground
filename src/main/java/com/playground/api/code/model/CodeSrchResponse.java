package com.playground.api.code.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "CodeSrchResponse", description = "화면에서 사용 할 코드 응답 데이터")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CodeSrchResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "코드")
  private String code;

  @Schema(description = "코드명")
  private String codeName;

  @Schema(description = "코드값")
  private String codeValue;

  @Schema(description = "사용여부")
  private String useYn;

  @Schema(description = "상위코드")
  private String upperCode;

  @Schema(description = "정렬순서")
  private Integer order;
}
