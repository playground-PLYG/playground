package com.playground.api.vote.model;

import java.io.Serial;
import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "VoteAnswerRequest", description = "사용자 답변들 조회 요청에 필요한 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class VoteAnswerRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 답변사용자ID
   */
  @Schema(description = "답변사용자ID", example = "sungjong2020")
  private String answerUserId;

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
   * 선택한항목일련번호들
   */
  @Schema(description = "선택한항목일련번호들", example = "{1,2,3,...}")
  private List<Integer> itemSsnoList;

}
