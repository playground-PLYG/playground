package com.playground.api.game.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@Table(name = "tb_game_result")
public class GameResultEntity extends BaseEntity {
  /**
   * 게임결과일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "game_result_sn")
  private Integer gameResultSn;

  /**
   * 게임유형코드
   */
  @Column(name = "game_ty_code")
  private String gameTyCode;

  /**
   * 별칭명
   */
  @Column(name = "ncm_cn")
  private String ncmCn;

  /**
   * 게임시간
   */
  @Column(name = "game_time")
  private Integer gameTime;

  /**
   * 게임1속성내용
   */
  @Column(name = "game_one_atrb_cn")
  private String gameOneAtrbCn;

  /**
   * 게임2속성내용
   */
  @Column(name = "game_two_atrb_cn")
  private String gameTwoAtrbCn;

  /**
   * 게임3속성내용
   */
  @Column(name = "game_three_atrb_cn")
  private String gameThreeAtrbCn;

  /**
   * 게임4속성내용
   */
  @Column(name = "game_four_atrb_cn")
  private String gameFourAtrbCn;

  /**
   * 게임5속성내용
   */
  @Column(name = "game_five_atrb_cn")
  private String gameFiveAtrbCn;

}
