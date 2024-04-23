package com.playground.api.metadata.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_metdata_prevew_image")
public class MetadataOpengraphImageEntity extends BaseEntity {
  /**
   * 미리보기이미지일련번호
   */
  @Id
  @Column(name = "prevew_image_Sn")
  private Integer prevewImageSn;

  /**
   * 미리보기이미지URL
   */
  @Column(name = "prevew_image_url")
  private String prevewImageUrl;

  /**
   * 미리보기이미지내용
   */
  @Column(name = "prevew_image_cn")
  private String prevewImageCn;

  @Builder
  public MetadataOpengraphImageEntity(Integer prevewImageSn, String prevewImageUrl, String prevewImageCn) {
    super();
    this.prevewImageSn = prevewImageSn;
    this.prevewImageUrl = prevewImageUrl;
    this.prevewImageCn = prevewImageCn;
  }

}
