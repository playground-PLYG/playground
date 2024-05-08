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

@Schema(name = "VoteResponse", description = "투표목록조회 및 등록수정 응답에 필요한 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteResponse extends BaseDto {

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
  private String anonymityVoteAlternative;

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
  private String voteDeleteAlternative;

  /**
   * DB insert, update, delete 결과
   */
  @Schema(description = "실행결과", example = "1")
  private String excuteResult;

  /** ==================================== QestnResponse ====================================*/
  /**
   * 질문내용
   */
  @Schema(description = "질문내용", example = "회식 가능한 날짜를 선택해주세요")
  private String questionContents;
  
  /**
   * 질문일련번호
   */
  @Schema(description = "질문일련번호", example = "1234567890")
  private Integer questionSsno;
  
  /**
   * 복수선택여부
   */
  @Schema(description = "복수선택여부", example = "Y")
  private String compoundNumberChoiceAlternative;
  
  /** ==================================== VoteIemResponse ====================================*/
  /**
   * 항목ID
   */
  @Schema(description = "항목ID", example = "0000123456")
  private String itemId;
  
  /**
   * 항목명
   */
  @Schema(description = "항목명", example = "또성골뱅이")
  private String itemName;
  /** =========================================================================================*/
  
  
  @Schema(name = "qestnList",description = "질문객체", example = "qestnResponse")
  private List<QestnResponse> qestnResponseList;
  
  @Schema(name = "voteIemList" ,description = "항목객체", example = "voteIemResponse")
  private List<VoteIemResponse> voteIemResponseList;
}
