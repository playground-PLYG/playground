package com.playground.api.post.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.playground.api.post.entity.PostEntity;
import com.playground.api.post.model.PostRequest;
import com.playground.api.post.model.PostResponse;
import com.playground.api.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final ModelMapper modelMapper;

	/** 전체 게시물 목록 조회 */
	public List<PostResponse> getPostList(String bbsId) {
		List<PostEntity> postEntity = postRepository.findAllByBbsId(bbsId);
		return postEntity.stream().map(item -> modelMapper.map(item, PostResponse.class)).toList();
	}
	
	/** 게시물 생성 */
	public PostResponse addPost(PostRequest postRequest) {
		PostEntity postEntity = PostEntity.builder()
				.nttNo(postRequest.getNttNo())
				.bbsId(postRequest.getBbsId())
				.nttSj(postRequest.getNttSj())
				.nttCn(postRequest.getNttCn())
				.registUsrId(postRequest.getRegistUsrId())
				.updtUsrId(postRequest.getUpdtUsrId())
				.build();
		postRepository.save(postEntity);
		
		return null;
	}
}
