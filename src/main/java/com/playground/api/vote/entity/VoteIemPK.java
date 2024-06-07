package com.playground.api.vote.entity;

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
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class VoteIemPK implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 항목ID
	 */
	private Integer iemSn;

	/**
	 * 질문일련번호
	 */
	private Integer qestnSn;

	/**
	 * 투표일련번호
	 */
	private Integer voteSn;
}
