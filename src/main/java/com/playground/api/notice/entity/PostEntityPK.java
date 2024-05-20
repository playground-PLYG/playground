package com.playground.api.notice.entity;

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
public class PostEntityPK implements Serializable {
	@Serial
  private static final long serialVersionUID = 1L;
	
	/** 게시물 번호 */
	private Integer nttSn;
	
	/** 게시판ID */
	private String noticeEntity;
	
}
