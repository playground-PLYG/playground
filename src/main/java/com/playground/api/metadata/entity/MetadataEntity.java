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
@Table(name = "metadata")
public class MetadataEntity extends BaseEntity {
  /**
   * URL
   */
  @Id
  @Column
  private String url;

  /**
   * title
   */
  @Column
  private String title;

  /**
   * description
   */
  @Column
  private String description;

  /**
   * category
   */
  @Column
  private String category;

  /**
   * 이름
   */
  @Column(name = "og_title")
  private String ogTitle;

  /**
   * og_description
   */
  @Column(name = "og_description")
  private String ogDescription;

  /**
   * og_url
   */
  @Column(name = "og_url")
  private String ogUrl;

  /**
   * og_site_name
   */
  @Column(name = "og_site_name")
  private String ogSiteName;

  /**
   * og image 목록
   */
  /*
   * @OneToMany
   *
   * @JoinColumn(name = "url") private List<MetadataOpengraphImageEntity> ogImages;
   */
  /**
   * metadata_base
   */
  @Column(name = "metadata_base")
  private String metadataBase;

  /**
   * icon url
   */
  @Column
  private String icon;

  /**
   * apple icon url
   */
  @Column
  private String apple;
}
