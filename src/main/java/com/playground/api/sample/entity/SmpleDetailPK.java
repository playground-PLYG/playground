package com.playground.api.sample.entity;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class SmpleDetailPK implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 샘플일련번호 SmpleDetailEntity에 관계 지정한 필드와 일치해야함
   */
  private Integer smpleEntity;

  /**
   * 샘플상세일련번호
   */
  private Integer smpleDetailSn;
}
