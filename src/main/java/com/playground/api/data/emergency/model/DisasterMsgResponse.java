package com.playground.api.data.emergency.model;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DisasterMsgResponse {
  @Schema(description = "해더")
  private DisasterMsgHeaderResponse header;

  @Schema(description = "페이지 당 개수")
  private int numOfRows;

  @Schema(description = "페이지 번호")
  private int pageNo;

  @Schema(description = "총 개수")
  private int totalCount;

  @Default
  @Schema(description = "결과 내용")
  private List<DisasterMsgBodyResponse> body = new ArrayList<>();
}
