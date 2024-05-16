package com.playground.api.post.model;

import java.util.List;

import com.playground.api.post.entity.PostEntity.PostEntityBuilder;
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

@Schema(name = "PostResponse", description = "게시물 CRUD")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PostResponse extends BaseDto {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Schema(description = "게시물번호")
	private int noticeNo;

	@Schema(description = "게시판ID")
	private String boardId;

	@Schema(description = "게시물제목")
	private String noticeSj;
	
	@Schema(description = "게시물내용")
	private String noticeCn;

	@Schema(description = "등록사용자ID")
	private String registUsrId;

	@Schema(description = "수정사용자ID")
	private String updtUsrId;
	
	
}

