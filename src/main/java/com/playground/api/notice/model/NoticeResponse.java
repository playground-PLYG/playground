package com.playground.api.notice.model;

import java.time.LocalDateTime;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "NoticeResponse", description = "게시판 CRUD")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class NoticeResponse extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "게시판ID")
	private String bbsID;
	
	@Schema(description = "게시판명")
	private String bbsNm;
	
	@Schema(description = "등록사용자ID")
	private String registUsrId;
	
	@Schema(description = "등록일시")
	private LocalDateTime registDt;
	
	@Schema(description = "수정사용자ID")
	private String updtUsrId;
	
	@Schema(description = "수정일시")
	private LocalDateTime updtDt;
	
}
