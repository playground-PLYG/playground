package com.playground.api.restaurant.entity;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class RstrntFilePK implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  /**
   * 식당일련번호
   */
  private Integer rstrntSn;

  /**
   * 파일일련번호
   */
  private Integer fileSn;
}
