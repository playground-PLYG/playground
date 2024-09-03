package com.playground.api.data.floodedcar.model;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "FloodedCarRequest", description = "침수차 조회 Request")
@NoArgsConstructor
@Getter
@Setter
public class FloodedCarRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "차량번호")
  private String nowVhclNo;

  @Schema(description = "페이지 당 row")
  private int numOfRows = 30;

  @Schema(description = "페이지번호")
  private int pageNo = 1;
}
