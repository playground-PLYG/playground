package com.playground.api.notice.model;

import java.util.List;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(name = "CommnetResponse", description = "댓글 CRUD")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CommentResponse extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "댓글번호")
	private int commentNo;
	
	@Schema(description = "게시물번호")
	private int noticeNo;
	
	@Schema(description = "게시판ID")
	private String boardId;
	
	@Schema(description = "댓글내용")
	private String commentCn;
	
	@Schema(description = "상위댓글번호")
	private int upperCommentNo;
	
	@Schema(description = "등록사용자ID")
	private String registUsrId;

	@Schema(description = "수정사용자ID")
	private String updtUsrId;
	
	private List<CommentResponse> commentList;
	
	
}
