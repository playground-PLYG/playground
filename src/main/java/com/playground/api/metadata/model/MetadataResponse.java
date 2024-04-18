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
   * 메타데이터URL
   */
  private String metdataUrl;

  /**
   * 메타데이터제목
   */
  private String metdataSj;

  /**
   * 메타데이터설명
   */
  private String metdataDc;

  /**
   * 메타데이터카테고리
   */
  private String metdataCategory;

  /**
   * keyword 목록
   */
  private List<String> keywords;

  /**
   * 미리보기이미지제목
   */
  private String prevewImageSj;

  /**
   * 미리보기이미지설명
   */
  private String prevewImageDc;

  /**
   * 미리보기이미지URL
   */
  private String prevewImageUrl;

  /**
   * 미리보기사이트명
   */
  private String prevewSiteNm;

  /**
   * og image 목록
   */
  private List<String> ogImages;

  /**
   * 메타데이터기본내용
   */
  private String metdataBassCn;

  /**
   * 메타데이터아이콘명
   */
  private String metdataIconNm;

}
