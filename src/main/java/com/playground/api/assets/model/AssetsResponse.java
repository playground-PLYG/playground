package com.playground.api.assets.model;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "AssetsResponse", description = "자산 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class AssetsResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;
  
  @Schema(description = "자산일련번호")
  private Integer assetsNo;
  
  @Schema(description = "자산카테고리")
  private String assetsCategory;
  
  @Schema(description = "자산명칭")
  private String assetsName;
  
  @Schema(description = "자산설명")
  private String assetsDescription;
  
  @Schema(description = "자산금액")
  private BigDecimal assetsPrice;
  
  @Schema(description = "사용여부")
  private String useYn;
  
  @Schema(description = "등록일시", example = "yyyy-mm-dd hh:mm:ss")
  private LocalDateTime registDate;

  @Schema(description = "수정일시", example = "yyyy-mm-dd hh:mm:ss")
  private LocalDateTime updtDate;
}
