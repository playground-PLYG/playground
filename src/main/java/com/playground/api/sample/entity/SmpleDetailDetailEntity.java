package com.playground.api.sample.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "tb_smple_detail_detail")
@IdClass(SmpleDetailDetailPK.class)
public class SmpleDetailDetailEntity extends BaseEntity {
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
  @Column(name = "smple_detail_sn")
  private Integer smpleDetailSn;

  /**
   * 샘플상세일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "smple_detail_detail_sn")
  private Integer smpleDetailDetailSn;

  /**
   * 샘플상세첫번째내용
   */
  @Column(name = "smple_detail_detail_first_cn")
  private String smpleDetailDetailFirstCn;

  /**
   * 샘플상세두번째내용
   */
  @Column(name = "smple_detail_detail_secon_cn")
  private String smpleDetailDetailSeconCn;

  /**
   * 샘플상세세번째내용
   */
  @Column(name = "smple_detail_detail_thrd_cn")
  private String smpleDetailDetailThrdCn;
}
