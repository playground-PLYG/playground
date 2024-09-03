package com.playground.api.data.floodedcar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FloodedCarBodyItemResponse {
  @Schema(description = "차량번호")
  private String nowVhclNo;

  @Schema(description = "사고 발생일시")
  private String acdnOccrDtm;

  @Schema(description = "사고 종류")
  private String acdnKindNm;

  @Schema(description = "자료작성일자")
  private String dtaWrtDt;
}
