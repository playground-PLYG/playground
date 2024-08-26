package com.playground.api.game.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "GameRankAddRequest", description = "게임 랭크 저장 요청 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class GameRankAddRequest extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 게임유형코드
   */
  @NotNull(message = "게임유형코드는 필수 값 입니다.")
  @Schema(description = "게임유형코드", requiredMode = RequiredMode.REQUIRED)
  private String gameTyCode;

  /**
   * 별칭명
   */
  @NotNull(message = "닉네임은 필수 값 입니다.")
  @Schema(description = "별칭명", requiredMode = RequiredMode.REQUIRED)
  private String ncmCn;

  /**
   * 게임시간
   */
  @Schema(description = "게임시간")
  private Integer gameTime;

  /**
   * 게임1속성내용
   */
  @Schema(description = "게임1속성내용")
  private String gameOneAtrbCn;

  /**
   * 게임2속성내용
   */
  @Schema(description = "게임2속성내용")
  private String gameTwoAtrbCn;

  /**
   * 게임3속성내용
   */
  @Schema(description = "게임3속성내용")
  private String gameThreeAtrbCn;

  /**
   * 게임4속성내용
   */
  @Schema(description = "게임4속성내용")
  private String gameFourAtrbCn;

  /**
   * 게임5속성내용
   */
  @Schema(description = "게임5속성내용")
  private String gameFiveAtrbCn;

}
