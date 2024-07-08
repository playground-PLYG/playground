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
   * 질문일련번호
   */
  @Schema(description = "질문일련번호", example = "1234567890")
  private Integer questionSsno;

  /**
   * 질문내용
   */
  @Schema(description = "질문내용", example = "질문내용")
  private String questionContents;


  /**
   * 투표노출여부
   */
  @Schema(description = "중복투표여부", example = "Y")
  private String compoundNumberChoiceAlternative;

  /**
   * 투표전송여부
   */
  @Schema(description = "익명투표여부", example = "Y")
  private String anonymityVoteAlternative;


  /**
   * detailDetail
   */
  @Schema(description = "투표결과 항목별", example = "count, ssno, name, etc...")
  private List<VoteResultDetailResponse> resultDetailList;

}
