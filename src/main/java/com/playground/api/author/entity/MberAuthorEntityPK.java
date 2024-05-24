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
public class MberAuthorEntityPK implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 회원ID */
  private String mberId;

  /** 권한ID */
  private String authorId;
}
