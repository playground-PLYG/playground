package com.playground.api.menu.model;

import java.io.Serial;
import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "MenuRequest", description = "메뉴 관리")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MenuRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "메뉴일련번호")
  private Integer menuSn;

  @Schema(description = "메뉴명")
  private String menuNm;

  @Schema(description = "메뉴URL")
  private String menuUrl;

  @Schema(description = "메뉴계층번호")
  private Integer menuDepth;

  @Schema(description = "정렬순서")
  private Integer menuSortOrdr;

  @Schema(description = "상위메뉴일련번호")
  private Integer upperMenuSn;

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
