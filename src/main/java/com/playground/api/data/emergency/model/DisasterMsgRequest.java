package com.playground.api.data.emergency.model;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "DisasterMsgRequest", description = "긴급재난문자 조회 Request")
@NoArgsConstructor
@Getter
@Setter
public class DisasterMsgRequest implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "페이지 당 row")
  private int numOfRows = 30;

  @Schema(description = "페이지번호")
  private int pageNo = 1;

  @Schema(description = "조회시작일자(YYYYMMDD)")
  private String crtDt;

  @Schema(description = "지역명(시도명, 시군구명)")
  private String rgnNm;
}
