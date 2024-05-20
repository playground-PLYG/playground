package com.playground.api.notice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "CommnetResponse", description = "댓글 CRUD")
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(of = {"commentNo", "noticeNo","boardId", "commentCn", "upperCommentNo", "commentList"})
public class CommentResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//implements BaseDto로 바꿔야함 
	//ToString commentList 넣으면 오류가 남
	
	@Schema(description = "댓글번호")
	private Integer commentNo;
	
	@Schema(description = "게시물번호")
	private Integer noticeNo;
	
	@Schema(description = "게시판ID")
	private String boardId;
	
	@Schema(description = "댓글내용")
	private String commentCn;
	
	@Schema(description = "상위댓글번호")
	private Integer upperCommentNo;
	
	@Schema(description = "등록사용자ID")
	private String registUsrId;

	@Schema(description = "수정사용자ID")
	private String updtUsrId;
	
	@Builder.Default
	private List<CommentResponse> commentList = new ArrayList<>();
	
}
