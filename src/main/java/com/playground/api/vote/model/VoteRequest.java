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
   * 사용자ID
   */
  @Schema(description = "사용자ID", example = "sungjong")
  private String answerUserId;

  /**
   * 질문일련번호
   */
  @Schema(description = "질문일련번호", example = "12347890")
  private Integer questionSsno;


  //////////////////////////////////////////
  // 사용하는 변수는 위로 올리기 -> 추후 사용안하면 삭제 예정
  //////////////////////////////////////////
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
   * 투표노출여부
   */
  @Schema(description = "투표노출여부", example = "Y")
  private String voteExposureAlternative;

  /**
   * 투표전송여부
   */
  @Schema(description = "투표전송여부", example = "Y")
  private String voteTransmissionAlternative;

  /**
   * 투표전송코드
   */
  @Schema(description = "투표전송코드", example = "NOW")
  private String voteTransmissionCode;


  /**
   * 질문들
   */
  @Schema(description = "질문객체", example = "voteQestnRequestList")
  private List<VoteQestnRequest> voteQestnRequestList;
}
