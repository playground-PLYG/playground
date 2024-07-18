package com.playground.api.metadata.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import com.playground.model.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MetadataResponse extends BaseDto {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 메타데이터URL
   */
  private String url;

  /**
   * 메타데이터제목
   */
  private String title;

  /**
   * 메타데이터설명
   */
  private String description;

  /**
   * 메타데이터카테고리
   */
  private String category;

  /**
   * keyword 목록
   */
  @Default
  private List<String> keywords = new ArrayList<>();

  /**
   * 미리보기이미지제목
   */
  private String ogTitle;

  /**
   * 미리보기이미지설명
   */
  private String ogDescription;

  /**
   * 미리보기이미지URL
   */
  private String ogUrl;

  /**
   * 미리보기사이트명
   */
  private String ogSiteName;

  /**
   * og image 목록
   */
  @Default
  private List<String> ogImages = new ArrayList<>();

  /**
   * 메타데이터기본내용
   */
  private String metdataBassCn;

  /**
   * 메타데이터아이콘명
   */
  private String icon;

}
