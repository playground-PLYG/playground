package com.playground.api.menu.model;

import java.io.Serial;
import java.time.LocalDateTime;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "MenuResponse", description = "메뉴 조회 응답 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MenuResponse extends BaseDto {

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

  @Schema(description = "하위메뉴보유여부")
  private String lwprtMenuHoldAt;

  @Schema(description = "메타데이터설명")
  private String metdataSj;

  @Schema(description = "미리보기이미지URL")
  private String prevewImageUrl;

}
