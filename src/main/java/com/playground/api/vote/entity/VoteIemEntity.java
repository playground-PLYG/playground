package com.playground.api.vote.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "tb_vote_iem")
@IdClass(VoteIemPK.class)
public class VoteIemEntity extends BaseEntity {

  /**
   * 항목ID
   */
  @Id
  @Column(name = "iem_id")
  private String iemId;

  /**
   * 투표일련번호
   */
  @Id
  @Column(name = "vote_sn")
  private Integer voteSn;

  /**
   * 질문일련번호
   */
  @Id
  @Column(name = "qestn_sn")
  private Integer qestnSn;

  /**
   * 항목명
   */
  @Column(name = "iem_nm")
  private String iemNm;

}
