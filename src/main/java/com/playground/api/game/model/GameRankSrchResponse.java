package com.playground.api.game.model;

import java.io.Serial;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "GameRankResponse", description = "게임 랭크 조회 응답 데이터")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class GameRankSrchResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 게임유형코드
   */
  @Schema(description = "게임유형코드")
  private String gameTyCode;

  /**
   * 별칭명
   */
  @Schema(description = "별칭명")
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
