package com.playground.api.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.api.post.model.PostRequest;
import com.playground.api.post.model.PostResponse;
import com.playground.api.post.service.PostService;
import org.springframework.web.bind.annotation.RequestBody;

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
	@Operation(summary = "게시물 전체 조회", description = "게시물 생성")
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
	
}
