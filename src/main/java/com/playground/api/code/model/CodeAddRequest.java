package com.playground.api.code.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "CodeAddRequest", description = "등록/수정 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class CodeAddRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "코드일련번호")
  private Integer codeSerialNo;

  @Schema(description = "코드ID")
  private String code;

  @Schema(description = "코드명")
  private String codeName;

  @Schema(description = "코드값")
  private String codeValue;

  @Schema(description = "사용여부")
  private String useYn;

  @Schema(description = "상위코드ID")
  private String upperCode;

  @Schema(description = "그룹코드여부")
  private String groupCode;

  @Schema(description = "정렬순번")
  private Integer order;



}
