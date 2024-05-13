package com.playground.api.post.entity;

import java.io.Serializable;

import com.playground.api.sample.entity.SmpleDetailPK;

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
	private static final long serialVersionUID = 1L;
	
	/** 게시물 번호 */
	private int nttNo;
	
	/** 게시판ID */
	private String bbsId;
	
}
