package com.playground.api.sample.entity;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class SmpleDetailDetailPK implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

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
