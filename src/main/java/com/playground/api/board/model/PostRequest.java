package com.playground.api.board.model;

import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "PostRequest", description = "게시물 CRUD")
@EqualsAndHashCode(callSuper = true)
@Getter
public class PostRequest extends BaseDto {

  private static final long serialVersionUID = 1L;

  @Schema(description = "게시물번호")
  private int noticeNo;

  @Schema(description = "게시판ID")
  private String boardId;

  @Schema(description = "게시물제목")
  private String noticeSj;

  @Schema(description = "게시물내용")
  private String noticeCn;

  @Schema(description = "등록사용자ID")
  private String registUsrId;

  @Schema(description = "수정사용자ID")
  private String updtUsrId;
}
