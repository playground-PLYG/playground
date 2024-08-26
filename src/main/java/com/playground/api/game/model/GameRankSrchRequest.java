package com.playground.api.game.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "GameRankRequest", description = "게임 랭크 조회 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class GameRankSrchRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 게임유형코드
   */
  @NotNull(message = "게임유형코드는 필수 값 입니다.")
  @Schema(description = "게임유형코드")
  private String gameTyCode;
}
