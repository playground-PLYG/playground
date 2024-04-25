package com.playground.api.vote.model;

import java.io.Serial;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "QestnAnswerRequest", description = "사용자 답변들 조회 요청에 필요한 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class QestnAnswerRequest extends BaseDto {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 답변일련번호
	 */
	@Schema(description = "답변일련번호", example = "1234567890")
	private Integer answerSsno;

	/**
	 * 질문일련번호
	 */
	@Schema(description = "질문일련번호", example = "1234567890")
	private Integer questionSsno;

	/**
	 * 투표일련번호
	 */
	@Schema(description = "투표일련번호", example = "1234567890")
	private Integer voteSsno;

	/**
	 * 항목ID
	 */
	@Schema(description = "항목ID", example = "0000123456")
	private String itemId;

	/**
	 * 답변사용자ID
	 */
	@Schema(description = "답변사용자ID", example = "sungjong2020")
	private String answerUserId;

	/**
	 * 답변내용
	 */
	@Schema(description = "답변내용", example = "4월 25일 목요일")
	private String answerContents;
}
