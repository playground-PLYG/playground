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
   * 등록사용자ID
   */
  @Schema(description = "등록사용자ID")
  private String registUserId;

  /**
   * 등록일시
   */
  @Schema(description = "등록일시")
  private LocalDateTime registDate;

  /**
   * 수정사용자ID
   */
  @Schema(description = "수정사용자ID")
  private String updateUserId;

  /**
   * 수정일시
   */
  @Schema(description = "수정일시")
  private LocalDateTime updateDate;

  /**
   * 질문객체
   */
  @Schema(description = "질문객체", example = "voteQestnResponse")
  private List<VoteQestnResponse> voteQestnResponseList;
}
