package com.playground.api.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.notice.entity.NoticeEntity;
import com.playground.api.notice.entity.PostEntity;
import com.playground.api.notice.entity.PostEntityPK;
import com.playground.api.notice.model.PostRequest;
import com.playground.api.notice.model.PostResponse;
import com.playground.api.notice.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	/** 전체 게시물 목록 조회 */
	@Transactional(readOnly = true)
	public List<PostResponse> getPostList(PostRequest req) {
	  
	  PostEntityPK pk = PostEntityPK.builder()
	  .noticeEntity(req.getBbsId())
	  .nttSn(req.getNttSn())
	  .build();
	  
		List<PostEntity> postEntity = postRepository.findByNoticeEntity(pk);
		return postEntity.stream().map(entity -> PostResponse.builder()
				.nttSn(entity.getNttSn())
				.bbsId(entity.getNoticeEntity().getBbsId())
				.nttSj(entity.getNttSj())
				.nttCn(entity.getNttCn())
				.registUsrId(entity.getRegistUsrId())
				.updtUsrId(entity.getUpdtUsrId())
		        .build())
		        .toList();
	}
	
	/** 게시물 생성 */
	@Transactional
	public PostResponse addPost(PostRequest postRequest) {
		PostEntity postEntity = PostEntity.builder()
		    .noticeEntity(NoticeEntity.builder().bbsId(postRequest.getBbsId()).build())
				.nttSj(postRequest.getNttSj())
				.nttCn(postRequest.getNttCn())
				.build();
		postRepository.save(postEntity);
		
		return PostResponse.builder()
				.bbsId(postEntity.getNoticeEntity().getBbsId())
				.nttSj(postEntity.getNttSj())
				.nttCn(postEntity.getNttCn())
				.registUsrId(postEntity.getRegistUsrId())
				.updtUsrId(postEntity.getUpdtUsrId())
				.build();
	}
	
	/**  게시물 수정  */ 
	@Transactional
	public PostResponse modifyPost(PostRequest postRequest) {
		PostEntity postEntity = PostEntity.builder()
				.nttSn(postRequest.getNttSn())
				.noticeEntity(NoticeEntity.builder().bbsId(postRequest.getBbsId()).build())
				.nttSj(postRequest.getNttSj())
				.nttCn(postRequest.getNttCn())
				.build();
		postRepository.save(postEntity);
		
		return PostResponse.builder()
				.nttSn(postEntity.getNttSn())
				.bbsId(postEntity.getNoticeEntity().getBbsId())
				.nttSj(postEntity.getNttSj())
				.nttCn(postEntity.getNttCn())
				.registUsrId(postEntity.getRegistUsrId())
				.updtUsrId(postEntity.getUpdtUsrId())
				.build();
	}
	
	
	/** 게시판 삭제*/
	@Transactional
	public void removePost(PostRequest postRequest) {
		//postRepository.deleteByNttNo(postRequest.getNttNo());
	}
	
}
