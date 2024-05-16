package com.playground.api.notice.model;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "NoticeRequest", description = "게시판 CRUD")
@EqualsAndHashCode(callSuper = true)
@Getter
public class NoticeRequest extends BaseDto{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "게시판ID")
	private String boardId;
	
	@Schema(description = "게시판명")
	private String boardNm;
	
}
