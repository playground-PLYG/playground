package com.playground.api.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playground.api.notice.entity.CommentEntity;
import com.playground.api.notice.model.CommentRequest;
import com.playground.api.notice.model.CommentResponse;
import com.playground.api.notice.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
	final private CommentRepository commentRepository;
	
	/** 댓글 조회 */
	@Transactional(readOnly = true)
	public List<CommentResponse> getCommentList (int nttNo){
		List<CommentEntity> commentEntity = commentRepository.findAllByNttNo(nttNo);
		
		return commentEntity.stream().map(entity -> CommentResponse.builder()
				.commentNo(entity.getCmntNo())
				.noticeNo(entity.getNttNo())
				.boardId(entity.getBbsId())
				.commentCn(entity.getCmntCn())
				.upperCommentNo(entity.getUpperCmntNo())
				.build())
		        .toList();
	}
	
	/** 댓글 생성 */
	@Transactional
	public List<CommentResponse> addComment (CommentRequest commentRequest){
		CommentEntity commentEntity = CommentEntity.builder()
				.nttNo(commentRequest.getNoticeNo())
				.bbsId(commentRequest.getBoardId())
				.cmntCn(commentRequest.getCommentCn())
				.upperCmntNo(commentRequest.getUpperCommentNo())
				.build();
		commentRepository.save(commentEntity);
		
		return null;
	}
	
	/** 댓글 수정 */
	@Transactional
	public void modifyComment (CommentRequest commentRequest) {
		CommentEntity commentEntity = CommentEntity.builder()
				.cmntNo(commentRequest.getCommentNo())
				.nttNo(commentRequest.getNoticeNo())
				.bbsId(commentRequest.getBoardId())
				.cmntCn(commentRequest.getCommentCn())
				.upperCmntNo(commentRequest.getUpperCommentNo())
				.build();
		commentRepository.save(commentEntity);
	}
	
	/** 임시 댓글 삭제 */ 
	@Transactional
	public void removeComment (CommentRequest commentRequest) {
		commentRepository.deleteByCmntNo(commentRequest.getCommentNo());
		commentRepository.deleteByUpperCmntNo(commentRequest.getCommentNo());
	}
}
