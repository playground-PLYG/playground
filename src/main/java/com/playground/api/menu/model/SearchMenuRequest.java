package com.playground.api.menu.model;

import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "SaveMenuResponse", description = "메뉴 저장 요청 데이터")
@Getter
@Setter
public class SearchMenuRequest extends BaseDto {

  @Schema(description = "메뉴ID")
  private int menuId;

  @Schema(description = "메뉴명")
  private String menuNm;

  @Schema(description = "메뉴URL")
  private String menuUrl;

  @Schema(description = "메뉴레벨")
  private String menuLvl;

  @Schema(description = "정렬순서")
  private String menuSortOrder;

  @Schema(description = "상위메뉴ID")
  private String parentMenuId;

  @Schema(description = "등록자")
  private int regMbrNo;

  @Schema(description = "등록일시")
  private LocalDateTime regDt;

  @Schema(description = "수정자")
  private int mdfcnMbrNo;

  @Schema(description = "수정일시")
  private LocalDateTime mdfcnDt;
  
  @Schema(description = "사용여부")
  private String useYn;

}
