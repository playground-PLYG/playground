package com.playground.api.notice.model;

import java.time.LocalDateTime;
import java.util.List;

import com.playground.api.sample.model.SmpleDetailResponse;
import com.playground.api.sample.model.SmpleResponse;
import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "NoticeResponse", description = "게시판 CRUD")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class NoticeResponse extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "게시판ID")
	private String boardId;
	
	@Schema(description = "게시판명")
	private String boardNm;
	
	
}
