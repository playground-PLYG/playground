package com.playground.api.vote.entity;

import java.time.LocalDateTime;
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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_vote")
public class VoteEntity extends BaseEntity {

  /**
   * 투표일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "vote_sn")
  private Integer voteSn;

  /**
   * 투표제목
   */
  @Column(name = "vote_sj")
  private String voteSj;

  /**
   * 투표시작일시
   */
  @Column(name = "vote_begin_dt")
  private LocalDateTime voteBeginDt;

  /**
   * 투표종료일시
   */
  @Column(name = "vote_end_dt")
  private LocalDateTime voteEndDt;

  /**
   * 투표노출여부
   */
  @Column(name = "vote_expsr_at")
  private String voteExpsrAt;

  /**
   * 투표전송여부
   */
  @Column(name = "vote_trnsmis_at")
  private String voteTrnsmisAt;

  /**
   * 투표전송코드
   */
  @Column(name = "vote_trnsmis_code")
  private String voteTrnsmisCode;

}
