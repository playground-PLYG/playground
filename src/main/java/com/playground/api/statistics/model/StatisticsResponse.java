package com.playground.api.statistics.model;

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

@Schema(name = "StatisticsResponse", description = "사용자 답변들을 통계 내기 위한 response")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class StatisticsResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 전체 투표 수
   */
  @Schema(description = "전체 투표 수", example = "100")
  private Integer totalVoteCount;

  /**
   * 전체 투표자 수
   */
  @Schema(description = "전체 투표자 수", example = "100")
  private Integer totalVoterCount;

  /**
   * 질문숫자
   */
  @Schema(description = "질문 수", example = "2")
  private Integer questionCount;

  /**
   * 투표일련번호
   */
  @Schema(description = "투표일련번호", example = "1234567890")
  private Integer voteSsno;

  /**
   * 답변 detail
   */
  @Schema(description = "답변 detail", example = "Object")
  private List<StatisticsDetailResponse> staDetailList;
}
