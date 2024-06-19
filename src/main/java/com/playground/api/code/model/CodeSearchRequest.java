package com.playground.api.code.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "CodeSearchRequest", description = "요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class CodeSearchRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "코드ID")
  private String code;

  @Schema(description = "코드명")
  private String codeName;

  @Schema(description = "상위코드ID")
  private String upperCode;

  @Schema(description = "그룹코드여부")
  private String groupCode;


}
