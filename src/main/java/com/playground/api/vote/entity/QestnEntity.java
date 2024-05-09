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
@Table(name = "tb_qestn")
@IdClass(QestnPK.class)
@SequenceGenerator(
    name = "qestn_sn_seq",
    sequenceName = "tb_qestn_qestn_sn_seq",
    initialValue = 1,
    allocationSize = 1
    )
public class QestnEntity extends BaseEntity {
  
  /**
   * 질문일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qestn_sn_seq")
  @Column(name = "qestn_sn")
  private Integer qestnSn;

  /**
   * 투표일련번호
   */
  @Id
  @Column(name = "vote_sn")
  private Integer voteSn;

  /**
   * 질문내용
   */
  @Column(name = "qestn_cn")
  private String qestnCn;

  /**
   * 복수선택여부
   */
  @Column(name = "compno_choise_at")
  private String compnoChoiseAt;

}
