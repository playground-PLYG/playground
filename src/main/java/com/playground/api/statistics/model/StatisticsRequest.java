package com.playground.api.statistics.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "StatisticsRequest", description = "사용자 답변들을 통계 내기 위한 request")
@EqualsAndHashCode(callSuper = true)
@Getter
public class StatisticsRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 투표일련번호
   */
  @Schema(description = "투표일련번호", example = "1234567890")
  private Integer voteSsno;

  /**
   * 질문일련번호
   */
  @Schema(description = "질문일련번호", example = "1234567890")
  private Integer questionSsno;

  /**
   * 항목ID
   */
  @Schema(description = "항목ID", example = "0000123456")
  private Integer itemSsno;

  /**
   * 답변사용자ID
   */
  @Schema(description = "답변사용자ID", example = "sungjong")
  private String answerUserId;
}
