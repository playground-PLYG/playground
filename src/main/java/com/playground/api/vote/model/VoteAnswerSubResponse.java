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

@Schema(name = "VoteAnswerSubResponse", description = "사용자 답변들 조회 응답에 필요한 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteAnswerSubResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;
  /**
   * 항목일련번호
   */
  @Schema(description = "질문일련번호", example = "0000123456")
  private Integer questionSsno;

  /**
   * 사용자가 선택한 답변들
   */
  @Schema(description = "사용자가 선택한 답변들", example = "Object")
  private List<Integer> voteAnswerList;

}
