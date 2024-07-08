package com.playground.api.vote.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "VoteAnswerSubDetailResponse", description = "사용자 답변들 조회 응답에 필요한 데이터")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class VoteAnswerSubDetailResponse extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 선택한 항목일련번호
   */
  @Schema(description = "선택한 항목일련번호", example = "0000123456")
  private Integer choiceItemSsno;

}
