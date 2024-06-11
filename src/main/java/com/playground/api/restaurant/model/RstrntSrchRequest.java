package com.playground.api.restaurant.model;

import java.io.Serial;
import java.math.BigDecimal;
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

  @Schema(description = "식당일련번호")
  private Integer restaurantSerialNo;

  @Schema(description = "식당명")
  private String restaurantName;

  @Schema(description = "식당종류코드")
  private String restaurantKindCode;

  @Schema(description = "식당거리")
  private BigDecimal restaurantDistance;

  @Schema(description = "위도위치")
  private String la;

  @Schema(description = "경도위치")
  private String lo;

  @Schema(description = "카카오지도ID")
  private String kakaoMapId;

  @Schema(description = "이미지파일ID")
  private Integer imageFileId;
}
