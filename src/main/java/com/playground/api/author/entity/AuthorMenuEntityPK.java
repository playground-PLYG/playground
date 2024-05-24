package com.playground.api.author.entity;

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
public class AuthorMenuEntityPK implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 권한ID */
  private String authorId;

  /** 메뉴일련번호 */
  private Integer menuSn;
}
