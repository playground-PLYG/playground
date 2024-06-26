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
@Table(name = "tb_qestn_answer")
@IdClass(QestnAnswerPK.class)
@SequenceGenerator(name = "answer_sn_seq", sequenceName = "tb_qestn_answer_answer_sn_seq", initialValue = 1, allocationSize = 1)
public class QestnAnswerEntity extends BaseEntity {

  /**
   * 답변일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_sn_seq")
  @Column(name = "answer_sn")
  private Integer answerSn;

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
   * 항목 ID
   */
  @Id
  @Column(name = "iem_sn")
  private Integer iemSn;

  /**
   * 답변사용자ID
   */
  @Column(name = "answer_user_id")
  private String answerUserId;

  /**
   * 답변내용
   */
  @Column(name = "answer_cn")
  private String answerCn;
}
