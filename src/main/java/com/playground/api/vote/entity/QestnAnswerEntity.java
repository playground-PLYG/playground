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
@Table(name = "tb_qestn_answer")
@IdClass(QestnAnswerPK.class)
public class QestnAnswerEntity extends BaseEntity {

  /**
   * 답변사용자ID
   */
  @Id
  @Column(name = "answer_usr_id")
  private String answerUsrId;

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

}
