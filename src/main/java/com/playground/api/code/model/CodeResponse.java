package com.playground.api.code.model;

import java.io.Serial;
import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "CodeResponse", description = "응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CodeResponse extends BaseDto {
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

  @Schema(description = "등록사용자ID")
  private String registUsrId;

  @Schema(description = "등록일시")
  private LocalDateTime registDt;

  @Schema(description = "수정사용자ID")
  private String updtUsrId;

  @Schema(description = "수정일시")
  private LocalDateTime updtDt;

}
