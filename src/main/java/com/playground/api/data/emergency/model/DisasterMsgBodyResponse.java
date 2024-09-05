package com.playground.api.data.emergency.model;

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
public class DisasterMsgBodyResponse {
  @Schema(description = "일련번호")
  private String sn;

  @Schema(description = "생성일시")
  private String crtDt;

  @Schema(description = "메시지내용")
  private String msgCn;

  @Schema(description = "수신지역명")
  private String rcptnRgnNm;

  @Schema(description = "긴급단계명")
  private String emrgStepNm;

  @Schema(description = "재해구분명")
  private String dstSeNm;

  @Schema(description = "등록일자")
  private String regYmd;

  @Schema(description = "수정일자")
  private String mdfcnYmd;
}
