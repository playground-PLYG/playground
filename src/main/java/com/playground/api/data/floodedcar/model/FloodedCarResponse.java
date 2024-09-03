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
public class FloodedCarResponse {
  @Schema(description = "요청 결과")
  private FloodedCarResultResponse response;
}
