package com.playground.api.restaurant.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "RstrntSrchRequest", description = "요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RstrntSrchRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "식당명")
  private String restaurantName;

  @Schema(description = "식당종류코드")
  private String restaurantKindCode;

}
