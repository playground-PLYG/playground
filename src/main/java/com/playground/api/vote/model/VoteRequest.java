package com.playground.api.vote.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "VoteRequest", description = "투표상세조회, 내투표조회, 결과상세보기 API에 사용되는 Request")
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
}
