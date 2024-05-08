package com.playground.api.post.model;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Schema(name = "PostRequest", description = "게시물 CRUD")
@EqualsAndHashCode(callSuper = true)
@Getter
public class PostRequest extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "게시물번호")
	private int nttNo;

	@Schema(description = "게시판ID")
	private String bbsId;

	@Schema(description = "게시물제목")
	private String nttSj;
	
	@Schema(description = "게시물내용")
	private String nttCn;
	
	@Schema(description = "등록사용자ID")
	private String registUsrId;
	
	@Schema(description = "수정사용자ID")
	private String updtUsrId;
}
