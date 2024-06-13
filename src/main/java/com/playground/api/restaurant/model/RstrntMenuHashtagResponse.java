package com.playground.api.restaurant.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "RstrntMenuHashtagResponse", description = "식당 메뉴 hashtag 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RstrntMenuHashtagResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "식당일련번호")
  private Integer restaurantSerialNo;

  @Schema(description = "식당메뉴일련번호")
  private Integer restaurantMenuSerialNo;

  @Schema(description = "해시태그일련번호")
  private Integer hashtagSerialNo;

  @Schema(description = "해시태그명")
  private String hashtagName;
}
