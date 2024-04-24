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
public class SmpleDetailDetailPK implements Serializable {
  private static final long serialVersionUID = 7285079586723271055L;

  /**
   * 샘플일련번호
   */
  private Integer smpleSn;

  /**
   * 샘플상세일련번호
   */
  private Integer smpleDetailSn;

  /**
   * 샘플상세상세일련번호
   */
  private Integer smpleDetailDetailSn;
}
