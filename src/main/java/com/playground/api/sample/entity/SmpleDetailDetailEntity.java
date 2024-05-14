package com.playground.api.sample.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@SequenceGenerator(
    name = "smple_detail_detail_sn_seq",
      sequenceName = "tb_smple_detail_detail_smple_detail_detail_sn_seq",
      initialValue = 1,
      allocationSize = 1
  )
public class SmpleDetailDetailEntity extends BaseEntity {

  /*
   * 다대일 관계 매핑 해당 필드명은 SmpleDetailDetailPK에 있는 FK로 잡은 필드와 일치해야함
   */
  @Id
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name="smple_sn",referencedColumnName = "smple_sn"),
    @JoinColumn(name="smple_detail_sn",referencedColumnName = "smple_detail_sn")
        })
  private SmpleDetailEntity smpleDetailEntity;
  
  /**
   * 샘플상세일련번호
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="smple_detail_detail_sn_seq")
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
