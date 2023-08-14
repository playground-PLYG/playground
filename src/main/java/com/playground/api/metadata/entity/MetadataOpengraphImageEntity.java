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
@Table(name = "metadata_opengraph_image")
public class MetadataOpengraphImageEntity extends BaseEntity {
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
   * image URL
   */
  @Column
  private String image;

  @Builder
  public MetadataOpengraphImageEntity(String seq, String url, String image) {
    super();
    this.seq = seq;
    this.url = url;
    this.image = image;
  }
}
