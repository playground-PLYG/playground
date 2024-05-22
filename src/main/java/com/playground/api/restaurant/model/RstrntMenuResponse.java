package com.playground.api.restaurant.model;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.playground.api.hashtag.model.HashtagResponse;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "RstrntMenuResponse", description = "식당 메뉴 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RstrntMenuResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "식당일련번호")
  private Integer restaurantSerialNo;

  @Schema(description = "식당메뉴일련번호")
  private Integer restaurantMenuSerialNo;

  @Schema(description = "식당명")
  private String menuName;

  @Schema(description = "메뉴가격")
  private BigDecimal menuPrice;

  @Default
  @Schema(description = "메뉴 해시태그 목록")
  private List<HashtagResponse> menuHashtagList = new ArrayList<>();
}
