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
public class SmpleDetailDetailPK implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 샘플상세일련번호 SmpleDetailDetailEntity에 관계 지정한 필드와 일치해야하고 타입은 PK만 잡는다
   */
  private SmpleDetailPK smpleDetailEntity;

  /**
   * 샘플상세상세일련번호
   */
  private Integer smpleDetailDetailSn;
}
