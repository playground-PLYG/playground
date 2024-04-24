package com.playground.api.code.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CodeResponse", description = "응답 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CodeResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "코드일련번호")
  private Integer codeSn;

  @Schema(description = "코드ID")
  private String codeId;

  @Schema(description = "코드명")
  private String codeNm;

  @Schema(description = "상위코드ID")
  private String upperCodeId;

  @Schema(description = "그룹코드여부")
  private String groupCodeAt;

  @Schema(description = "정렬순번")
  private Integer sortOrdr;

  @Schema(description = "등록사용자ID")
  private String registUsrId;

  @Schema(description = "등록일시")
  private String registDt;

  @Schema(description = "수정사용자ID")
  private String updtUsrId;

  @Schema(description = "수정일시")
  private String updtDt;



}
