package com.playground.api.data.floodedcar.model;

import java.util.List;
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
public class FloodedCarBodyItemsResponse {
  @Schema(description = "결과 항목")
  private List<FloodedCarBodyItemResponse> item;
}
