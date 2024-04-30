package com.playground.api.restaurant.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "RstrntMenuRemoveRequest", description = "식당 메뉴 삭제 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class RstrntMenuRemoveRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull(message = "식당일련번호는 필수 값 입니다.")
  @Schema(description = "식당일련번호", requiredMode = RequiredMode.REQUIRED)
  private Integer restaurantSerialNo;

  @NotNull(message = "식당메뉴일련번호는 필수 값 입니다.")
  @Schema(description = "식당메뉴일련번호", requiredMode = RequiredMode.REQUIRED)
  private Integer restaurantMenuSerialNo;
}
