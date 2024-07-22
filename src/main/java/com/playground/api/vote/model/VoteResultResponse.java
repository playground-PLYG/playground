package com.playground.api.vote.model;

import java.io.Serial;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "VoteResultResponse", description = "사용자 답변들을 통계 내기 위한 Detail response")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteResultResponse extends BaseDto {

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
   * 투표결과 시 사용하는 객체
   */
  @Schema(description = "투표결과객체", example = "voteResultList")
  private List<VoteResultDetailResponse> voteResultList;

}
