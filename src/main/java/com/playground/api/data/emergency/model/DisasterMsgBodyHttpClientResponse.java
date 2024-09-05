package com.playground.api.data.emergency.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class DisasterMsgBodyHttpClientResponse {
  @JsonProperty("SN")
  @Schema(description = "일련번호")
  private String sn;

  @JsonProperty("CRT_DT")
  @Schema(description = "생성일시")
  private String crtDt;

  @JsonProperty("MSG_CN")
  @Schema(description = "메시지내용")
  private String msgCn;

  @JsonProperty("RCPTN_RGN_NM")
  @Schema(description = "수신지역명")
  private String rcptnRgnNm;

  @JsonProperty("EMRG_STEP_NM")
  @Schema(description = "긴급단계명")
  private String emrgStepNm;

  @JsonProperty("DST_SE_NM")
  @Schema(description = "재해구분명")
  private String dstSeNm;

  @JsonProperty("REG_YMD")
  @Schema(description = "등록일자")
  private String regYmd;

  @JsonProperty("MDFCN_YMD")
  @Schema(description = "수정일자")
  private String mdfcnYmd;
}
