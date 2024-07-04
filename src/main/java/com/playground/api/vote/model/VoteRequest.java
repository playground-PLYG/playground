package com.playground.api.vote.model;

import java.io.Serial;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "VoteRequest", description = "투표목록조회 및 등록수정 요청에 필요한 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class VoteRequest extends BaseDto {

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
  private String voteBeginDate;

  /**
   * 투표종료일시
   */
  @Schema(description = "투표종료일시", example = "yyyy-mm-dd hh:mm:ss")
  private String voteEndDate;

  /**
   * 투표삭제여부
   */
  @Schema(description = "투표삭제여부", example = "N")
  private String voteDeleteAlternative;

  /**
   * 질문일련번호
   */
  @Schema(description = "질문일련번호", example = "12347890")
  private Integer questionSsno;

  @Schema(description = "질문객체", example = "qestnRequest")
  private List<VoteQestnRequest> qestnRequestList;
}
