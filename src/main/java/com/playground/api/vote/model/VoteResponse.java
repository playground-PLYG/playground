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
  @Schema(description = "투표일련번호", example = "12345890")
  private Integer voteSsno;

  /**
   * 투표종류코드
   */
  @Schema(description = "투표종류코드", example = "LUN")
  private String voteKindCode;

  /**
   * 투표종류명
   */
  @Schema(description = "투표종류명", example = "점심식사")
  private String voteKindName;

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

  @Schema(description = "등록사용자ID")
  private String registUsrId;

  @Schema(description = "등록일시")
  private LocalDateTime registDt;

  @Schema(description = "수정사용자ID")
  private String updtUsrId;

  @Schema(description = "수정일시")
  private LocalDateTime updtDt;

  /**
   * DB insert, update, delete 결과
   */
  @Schema(description = "실행결과", example = "1")
  private String excuteResult;

  @Schema(description = "질문객체", example = "qestnResponse")
  private List<VoteQestnResponse> qestnResponseList;
}
