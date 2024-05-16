package com.playground.api.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/** 전체 게시물 목록 조회 */
	@Transactional(readOnly = true)
	public List<PostResponse> getPostList(String bbsId) {
		List<PostEntity> postEntity = postRepository.findAllByBbsId(bbsId);
		return postEntity.stream().map(entity -> PostResponse.builder()
				.noticeNo(entity.getNttNo())
				.boardId(entity.getBbsId())
				.noticeSj(entity.getNttSj())
				.noticeCn(entity.getNttCn())
		        .build())
		        .toList();
	}
	
	/** 게시물 생성 */
	@Transactional
	public PostResponse addPost(PostRequest postRequest) {
		PostEntity postEntity = PostEntity.builder()
				.bbsId(postRequest.getBoardId())
				.nttSj(postRequest.getNoticeSj())
				.nttCn(postRequest.getNoticeCn())
				.build();
		postRepository.save(postEntity);
		
		return PostResponse.builder()
				.boardId(postEntity.getBbsId())
				.noticeSj(postEntity.getNttSj())
				.noticeCn(postEntity.getNttCn())
				.build();
	}
	
	/**  게시물 수정  */ 
	@Transactional
	public PostResponse modifyPost(PostRequest postRequest) {
		PostEntity postEntity = PostEntity.builder()
				.nttNo(postRequest.getNoticeNo())
				.bbsId(postRequest.getBoardId())
				.nttSj(postRequest.getNoticeSj())
				.nttCn(postRequest.getNoticeCn())
				.build();
		postRepository.save(postEntity);
		
		return PostResponse.builder()
				.noticeNo(postEntity.getNttNo())
				.boardId(postEntity.getBbsId())
				.noticeSj(postEntity.getNttSj())
				.noticeCn(postEntity.getNttCn())
				.build();
	}
	
	
	/** 게시판 삭제*/
	@Transactional
	public void removePost(PostRequest postRequest) {
		postRepository.deleteByNttNo(postRequest.getNoticeNo());
	}
	
}
