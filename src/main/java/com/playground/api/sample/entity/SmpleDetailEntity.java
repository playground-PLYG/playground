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
@Table(name = "tb_smple_detail")
public class SmpleDetailEntity extends BaseEntity {
  /**
   * 샘플일련번호
   */
  @Id
  @Column(name = "smple_sn")
  private Integer smpleSn;

  /**
   * 샘플상세일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "smple_detail_sn")
  private Integer smpleDetailSn;

  /**
   * 샘플상세첫번째내용
   */
  @Column(name = "smple_detail_first_cn")
  private String smpleDetailFirstCn;

  /**
   * 샘플상세두번째내용
   */
  @Column(name = "smple_detail_secon_cn")
  private String smpleDetailSeconCn;

  /**
   * 샘플상세세번째내용
   */
  @Column(name = "smple_detail_thrd_cn")
  private String smpleDetailThrdCn;
}
