package com.playground.api.notice.entity;

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
public class CommentEntityPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 댓글번호 */
	private int cmntNo;
	
	/** 게시물 번호*/
	private int nttNo;
	
	/** 게시판 ID */
	private String bbsId;
}
