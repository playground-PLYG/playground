package com.playground.api.restaurant.model;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "RstrntSrchResponse", description = "식당 조회 응답 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RstrntSrchResponse extends BaseDto {

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

  @Schema(description = "최근선택일시")
  private LocalDateTime recentChoiseDt;

  @Schema(description = "누적선택수")
  private Long accmltChoiseCo;

  @Schema(description = "이미지파일ID")
  private Integer imageFileId;

}
