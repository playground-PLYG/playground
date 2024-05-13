package com.playground.api.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.api.post.model.PostRequest;
import com.playground.api.post.model.PostResponse;
import com.playground.api.post.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "post", description = "게시물")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class PostController {
	private final PostService postService;
	/**
	 * 게시물 목록 조회
	 *
	 */
	@Operation(summary = "게시물 전체 조회", description = "게시물 조회")
	@GetMapping("/public/post/getPostList/{bbsId}")
	public List<PostResponse> getPostList(@PathVariable("bbsId") String bbsId) {
		return postService.getPostList(bbsId);
	}
	
	/**
	 * 게시물 목록 생성
	 *
	 */
	@Operation(summary = "게시물 생성", description = "게시물 생성")
	@PostMapping("/api/post/addPost")
	public PostResponse addPost(@RequestBody PostRequest postRequest) {
		
		return postService.addPost(postRequest);
	}
	
	@Operation(summary = "게시물 수정", description = "게시물 수정")
	@PostMapping("/api/post/addPostTest")
	public PostResponse modifyPost(@RequestBody PostRequest postRequest) {
		
		return postService.modifyPost(postRequest);
	}
	
	/**
	 * 게시물 삭제
	 *
	 */
	@Operation(summary = "게시판 삭제", description = "게시판 삭제")
	@DeleteMapping("/api/post/removePost")
	public void removePost(@RequestBody PostRequest postRequest) {
		 postService.removePost(postRequest);
	}
}
