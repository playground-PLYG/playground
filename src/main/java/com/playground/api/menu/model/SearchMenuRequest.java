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

  @Schema(description = "메뉴일련번호")
  private int menuSn;

  @Schema(description = "메뉴명")
  private String menuNm;

  @Schema(description = "메뉴URL")
  private String menuUrl;

  @Schema(description = "메뉴계층번호")
  private String menuDepth;

  @Schema(description = "정렬순서")
  private String menuSortOrdr;

  @Schema(description = "상위메뉴일련번호")
  private String upperMenuSn;
  
  @Schema(description = "사용여부")
  private String useAt;

  @Schema(description = "등록사용자ID")
  private String registUsrId;

  @Schema(description = "등록일시")
  private LocalDateTime registDt;

  @Schema(description = "수정사용자ID")
  private String updtUsrId;

  @Schema(description = "수정일시")
  private LocalDateTime updtDt;

}
