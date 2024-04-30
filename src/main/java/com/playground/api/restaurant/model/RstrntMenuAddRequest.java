package com.playground.api.restaurant.model;

import java.io.Serial;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "RstrntMenuAddRequest", description = "식당 메뉴 저장 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class RstrntMenuAddRequest extends RstrntMenuRequest {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "식당일련번호는 필수 값 입니다.")
  @Schema(description = "식당일련번호", requiredMode = RequiredMode.REQUIRED)
  private Integer restaurantSerialNo;

  @Null
  @Schema(description = "식당메뉴일련번호", hidden = true)
  private Integer restaurantMenuSerialNo;
}
