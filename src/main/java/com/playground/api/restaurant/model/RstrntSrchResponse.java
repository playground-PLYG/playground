package com.playground.api.restaurant.model;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "RstrntSrchResponse", description = "식당 조회 응답 데이터")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RstrntSrchResponse extends BaseDto {

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

  @Schema(description = "최근선택일시")
  private LocalDateTime recentChoiseDate;

  @Schema(description = "누적선택수")
  private Long accumulationChoiseCount;

  @Schema(description = "위도위치")
  private String la;

  @Schema(description = "경도위치")
  private String lo;

  @Schema(description = "카카오지도ID")
  private String kakaoMapId;

  @Schema(description = "이미지파일ID")
  private Integer imageFileId;


}
