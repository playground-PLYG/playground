package com.playground.api.metadata.model;

import java.util.List;
import com.playground.model.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MetadataResponse extends BaseDto {
  /**
   * URL
   */
  private String url;

  /**
   * title
   */
  private String title;

  /**
   * description
   */
  private String description;

  /**
   * category
   */
  private String category;

  /**
   * keyword 목록
   */
  private List<String> keywords;

  /**
   * 이름
   */
  private String ogTitle;

  /**
   * og_description
   */
  private String ogDescription;

  /**
   * og_url
   */
  private String ogUrl;

  /**
   * og_site_name
   */
  private String ogSiteName;

  /**
   * og image 목록
   */
  private List<String> ogImages;

  /**
   * metadata_base
   */
  private String metadataBase;

  /**
   * icon url
   */
  private String icon;

  /**
   * apple icon url
   */
  private String apple;
}
