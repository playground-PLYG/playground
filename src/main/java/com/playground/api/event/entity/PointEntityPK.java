package com.playground.api.event.entity;

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
public class PointEntityPK implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  // 맴버ID
  private String mberId;

  // 포인트일련번호
  private Integer pointSn;
}
