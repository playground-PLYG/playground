package com.playground.api.vote.model;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "VoteRstrntResponse", description = "식당메뉴투표 정보 응답")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteRstrntResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 투표일련번호
   */
  @Schema(description = "투표일련번호", example = "1234567890")
  private Integer voteSsno;

  /**
   * 투표종류코드
   */
  @Schema(description = "투표종류코드", example = "LUN")
  private String voteKindCode;

  /**
   * 투표제목
   */
  @Schema(description = "투표제목", example = "오점뭐")
  private String voteSubject;

  /**
   * 익명투표여부
   */
  @Schema(description = "익명투표여부", example = "Y")
  private String anonymityVoteYn;

  /**
   * 투표시작일시
   */
  @Schema(description = "투표시작일시", example = "yyyy-mm-dd hh:mm:ss")
  private LocalDateTime voteBeginDate;

  /**
   * 투표종료일시
   */
  @Schema(description = "투표종료일시", example = "yyyy-mm-dd hh:mm:ss")
  private LocalDateTime voteEndDate;

  /**
   * 투표삭제여부
   */
  @Schema(description = "투표삭제여부", example = "N")
  private String voteDeleteYn;
  
  /**
   * 질문일련번호
   */
  @Schema(description = "질문일련번호", example = "Y")
  private Integer qestnSsno;
  
  /**
   * 질문일련번호
   */
  @Schema(description = "질문명", example = "Y")
  private String qestnName;
  
  /**
   * 질문일련번호
   */
  @Schema(description = "복수선택여부", example = "Y")
  private String compnoChoiseYn;
  

  @Schema(description = "식당메뉴투표리스트")
  private List<VoteRstrntIemResponse> voteRstrntIemList;
}
