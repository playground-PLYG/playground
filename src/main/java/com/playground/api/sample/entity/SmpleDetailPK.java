package com.playground.api.sample.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class SmpleDetailPK implements Serializable {
  private static final long serialVersionUID = -5128058335266406815L;

  /**
   * 샘플일련번호
   */
  private Integer smpleSn;

  /**
   * 샘플상세일련번호
   */
  private Integer smpleDetailSn;
}
