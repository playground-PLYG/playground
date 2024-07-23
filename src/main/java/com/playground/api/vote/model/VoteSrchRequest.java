package com.playground.api.vote.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "VoteRequest", description = "투표목록조회 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class VoteSrchRequest extends BaseDto {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 사용자ID
   */
  @Schema(description = "사용자ID", example = "sungjong1111")
  private String userId;

  /**
   * 투표제목
   */
  @Schema(description = "투표제목", example = "오점뭐")
  private String voteSubject;

  /**
   * 투표진행상태
   */
  @Schema(description = "투표진행상태", example = "VTI")
  private String voteStatus;

}
