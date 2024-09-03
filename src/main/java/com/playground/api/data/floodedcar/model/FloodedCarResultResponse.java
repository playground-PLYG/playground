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
public class FloodedCarResultResponse {
  @Schema(description = "해더")
  private FloodedCarHeaderResponse header;

  @Schema(description = "본문")
  private FloodedCarBodyResponse body;
}
