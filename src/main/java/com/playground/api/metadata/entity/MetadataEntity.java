package com.playground.api.metadata.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_metdata")
public class MetadataEntity extends BaseEntity {
  /**
   * 메타데이터URL
   */
  @Id
  @Column(name = "metdata_url")
  private String metdataUrl;

  /**
   * 메타데이터제목
   */
  @Column(name = "metdata_sj")
  private String metdataSj;

  /**
   * 메타데이터설명
   */
  @Column(name = "metdata_dc")
  private String metdataDc;

  /**
   * 메타데이터카테고리
   */
  @Column(name = "metdata_category")
  private String metdataCategory;

  /**
   * 미리보기이미지제목
   */
  @Column(name = "prevew_image_sj")
  private String prevewImageSj;

  /**
   * 미리보기이미지설명
   */
  @Column(name = "prevew_image_dc")
  private String prevewImageDc;

  /**
   * 미리보기이미지URL
   */
  @Column(name = "prevew_image_url")
  private String prevewImageUrl;

  /**
   * 미리보기사이트명
   */
  @Column(name = "prevew_site_nm")
  private String prevewSiteNm;

  /**
   * 메타데이터기본내용
   */
  @Column(name = "metdata_bass_cn")
  private String metdataBassCn;

  /**
   * 메타데이터아이콘명
   */
  @Column(name = "metdata_icon_nm")
  private String metdataIconNm;

}
