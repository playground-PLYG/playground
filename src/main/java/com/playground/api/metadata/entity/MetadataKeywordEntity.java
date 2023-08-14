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
@Table(name = "metadata_keyword")
public class MetadataKeywordEntity extends BaseEntity {
  /**
   * seq
   */
  @Id
  @Column
  private String seq;

  /**
   * URL
   */
  @Column
  private String url;

  /**
   * keyword
   */
  @Column
  private String keyword;

  @Builder
  public MetadataKeywordEntity(String seq, String url, String keyword) {
    super();
    this.seq = seq;
    this.url = url;
    this.keyword = keyword;
  }
}
