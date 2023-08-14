package com.playground.api.metadata.entity;

import com.playground.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "metadata_opengraph_imageme")
public class MetadataKeywordEntity extends BaseEntity {
  /**
   * URL
   */
  @Id
  @Column
  private String url;

  /**
   * image URL
   */
  @Column
  private String image;
}
