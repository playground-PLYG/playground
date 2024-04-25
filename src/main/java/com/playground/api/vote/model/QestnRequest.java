package com.playground.api.vote.model;

import java.io.Serial;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "QestnRequest", description = "질문목록조회 및 등록,수정 요청에 필요한 데이터")
@EqualsAndHashCode(callSuper = true)
@Getter
public class QestnRequest extends BaseDto {

	@Serial
	private static final long serialVersionUID = 1L;

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
	 * 질문내용
	 */
	@Schema(description = "질문내용", example = "회식 가능한 날짜를 선택해주세요")
	private String questionContents;

	/**
	 * 복수선택여부
	 */
	@Schema(description = "복수선택여부", example = "Y")
	private String compoundNumberChoiceAlternative;
}
