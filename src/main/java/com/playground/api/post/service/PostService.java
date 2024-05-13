package com.playground.api.post.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.playground.api.post.entity.PostEntity;
import com.playground.api.post.model.PostRequest;
import com.playground.api.post.model.PostResponse;
import com.playground.api.post.repository.PostRepository;
import com.playground.api.sample.model.SmpleResponse;

import jakarta.transaction.Transactional;
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
		return postEntity.stream().map(entity -> PostResponse.builder()
				.nttNo(entity.getNttNo())
				.bbsId(entity.getBbsId())
				.nttSj(entity.getNttSj())
				.nttCn(entity.getNttCn())
				.registUsrId(entity.getRegistUsrId())
				.updtUsrId(entity.getUpdtUsrId())
		        .build())
		        .toList();
		
	}
	
	/** 게시물 생성 */
	public PostResponse addPost(PostRequest postRequest) {
		PostEntity postEntity = PostEntity.builder()
				.bbsId(postRequest.getBbsId())
				.nttSj(postRequest.getNttSj())
				.nttCn(postRequest.getNttCn())
				.registUsrId(postRequest.getRegistUsrId())
				.updtUsrId(postRequest.getUpdtUsrId())
				.build();
		postRepository.save(postEntity);
		
		return PostResponse.builder()
				.bbsId(postEntity.getBbsId())
				.nttSj(postEntity.getNttSj())
				.nttCn(postEntity.getNttCn())
				.registUsrId(postEntity.getRegistUsrId())
				.updtUsrId(postEntity.getUpdtUsrId())
				.build();
	}
	
	/**  게시물 수정  */ 
	public PostResponse modifyPost(PostRequest postRequest) {
		PostEntity postEntity = PostEntity.builder()
				.nttNo(postRequest.getNttNo())
				.bbsId(postRequest.getBbsId())
				.nttSj(postRequest.getNttSj())
				.nttCn(postRequest.getNttCn())
				.registUsrId(postRequest.getRegistUsrId())
				.updtUsrId(postRequest.getUpdtUsrId())
				.build();
		postRepository.save(postEntity);
		
		return PostResponse.builder()
				.nttNo(postEntity.getNttNo())
				.bbsId(postEntity.getBbsId())
				.nttSj(postEntity.getNttSj())
				.nttCn(postEntity.getNttCn())
				.registUsrId(postEntity.getRegistUsrId())
				.updtUsrId(postEntity.getUpdtUsrId())
				.build();
	}
	
	
	/** 게시판 삭제*/
	@Transactional
	public void removePost(PostRequest postRequest) {
		postRepository.deleteByNttNo(postRequest.getNttNo());
	}
	
}
