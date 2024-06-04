package com.playground.api.assets.model;

import java.io.Serial;
import java.math.BigDecimal;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "AssetsRequest", description = "자산 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class AssetsRequest extends BaseDto{

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
}
