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
public class FloodedCarHeaderResponse {
  @Schema(description = "결과 코드")
  private String resultCode;

  @Schema(description = "결과 메시지")
  private String resultMsg;
}