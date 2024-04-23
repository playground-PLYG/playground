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
@Table(name = "tb_metdata_kwrd")
public class MetadataKeywordEntity extends BaseEntity {
  /**
   * 키워드일련번호
   */
  @Id
  @Column(name = "kwrd_sn")
  private Integer kwrdSn;

  /**
   * 키워드URL
   */
  @Column(name = "kwrd_url")
  private String kwrdUrl;

  /**
   * 키워드내용
   */
  @Column(name = "kwrd_cn")
  private String kwrdCn;

  @Builder
  public MetadataKeywordEntity(Integer kwrdSn, String kwrdUrl, String kwrdCn) {
    super();
    this.kwrdSn = kwrdSn;
    this.kwrdUrl = kwrdUrl;
    this.kwrdCn = kwrdCn;
  }

}
