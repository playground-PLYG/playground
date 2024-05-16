package com.playground.api.notice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.api.notice.model.CommentRequest;
import com.playground.api.notice.model.CommentResponse;
import com.playground.api.notice.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "commnet", description = "댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class CommentController {

	private final CommentService commentService;
	/**
	 * 댓글 조회
	 *
	*/ 
	@Operation(summary = "댓글 조회", description = "댓글 조회")
	@GetMapping("/public/comment/getCommentList/{nttNo}")
	public List<CommentResponse> getCommentList(@PathVariable("nttNo") int nttNo) {
		return commentService.getCommentList(nttNo);
	}
	
	/**
	 * 댓글저장
	 */
	@Operation(summary = "댓글저장" , description = "댓글저장")
	@PostMapping("/api/comment/addComment")
	public List<CommentResponse> addComment(@RequestBody CommentRequest commentRequest){
		return commentService.addComment(commentRequest);
	}
	
	/**
	 * 댓들 수정
	 */
	@Operation(summary = "댓글수정", description = "댓글수정")
	@PostMapping("/api/comment/modifyComment")
	public void modifyComment(@RequestBody CommentRequest commentRequest) {
		commentService.modifyComment(commentRequest);
	}
	
	/*
	 * 댓글삭제
	 */
	@Operation(summary = "댓글삭제", description = "댓글삭제")
	@DeleteMapping("/api/comment/removeComment")
	public void removeComment(@RequestBody CommentRequest commentRequest) {
		commentService.removeComment(commentRequest);
	}
	
	
}
