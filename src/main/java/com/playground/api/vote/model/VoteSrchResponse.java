package com.playground.api.vote.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "VoteSrchResponse", description = "투표목록조회 응답에 필요한 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteSrchResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 투표일련번호
   */
  @Schema(description = "투표일련번호", example = "12345890")
  private Integer voteSsno;

  /**
   * 투표제목
   */
  @Schema(description = "투표제목", example = "오점뭐")
  private String voteSubject;

  /**
   * 투표시작일시
   */
  @Schema(description = "투표시작일시", example = "yyyy-mm-dd HH")
  private String voteBeginDate;

  /**
   * 투표종료일시
   */
  @Schema(description = "투표종료일시", example = "yyyy-mm-dd HH")
  private String voteEndDate;


  /**
   * 투표여부
   */
  @Schema(description = "투표참여여부")
  private String votePartcptnAt;



}
