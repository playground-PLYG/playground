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
public class FloodedCarBodyResponse {
  @Schema(description = "페이지 당 개수")
  private int numOfRows;

  @Schema(description = "페이지 번호")
  private int pageNo;

  @Schema(description = "총 개수")
  private int totalCount;

  @Schema(description = "결과 항목 객체")
  private FloodedCarBodyItemsResponse items;

}
