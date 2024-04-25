package com.playground.api.vote.entity;

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
public class VoteIemPK implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 항목ID
	 */
	private String iemId;

	/**
	 * 질문일련번호
	 */
	private Integer qestnSn;

	/**
	 * 투표일련번호
	 */
	private Integer voteSn;
}
