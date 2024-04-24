package com.playground.api.sample.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_smple")
public class SmpleEntity extends BaseEntity {
  /**
   * 샘플일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "smple_sn")
  private Integer smpleSn;

  /**
   * 샘플첫번째내용
   */
  @Column(name = "smple_first_cn")
  private String smpleFirstCn;

  /**
   * 샘플두번째내용
   */
  @Column(name = "smple_secon_cn")
  private String smpleSeconCn;

  /**
   * 샘플세번째내용
   */
  @Column(name = "smple_thrd_cn")
  private String smpleThrdCn;
}
