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
  private Integer rstrntSn;

  @Schema(description = "식당명")
  private String rstrntNm;

  @Schema(description = "식당종류코드")
  private String rstrntKndCode;
  
  @Schema(description = "식당거리")
  private BigDecimal rstrntDstnc;

  @Schema(description = "위도위치")
  private String laLc;

  @Schema(description = "경도위치")
  private String loLc;

  @Schema(description = "카카오지도ID")
  private String kakaoMapId;


}
