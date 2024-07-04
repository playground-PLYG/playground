package com.playground.api.vote.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "iem_sn_seq", sequenceName = "tb_vote_iem_iem_sn_seq", initialValue = 1, allocationSize = 1)
public class VoteIemEntity extends BaseEntity {

  /**
   * 항목일련번호
   */
  @Id
  @Column(name = "iem_sn")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iem_sn_seq")
  private Integer iemSn;

  /**
   * 투표 일련번호
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


  /**
   * 항목식별ID
   */
  @Column(name = "iem_idntfc_id")
  private String iemIdntfcId;

}
