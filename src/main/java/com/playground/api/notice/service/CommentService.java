package com.playground.api.notice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.notice.entity.CommentEntity;
import com.playground.api.notice.entity.CommentEntityPK;
import com.playground.api.notice.entity.NoticeEntity;
import com.playground.api.notice.entity.PostEntity;
import com.playground.api.notice.entity.PostEntityPK;
import com.playground.api.notice.model.CommentRequest;
import com.playground.api.notice.model.CommentResponse;
import com.playground.api.notice.repository.CommentRepository;
import com.playground.api.notice.repository.dsl.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
	
	/** 댓글 조회 */
	@Transactional(readOnly = true)
	public List<CommentEntity> getCommentList (CommentRequest req){
	  try {
	    CommentEntityPK pk = CommentEntityPK.builder().postEntity(
	        PostEntityPK.builder()
	        .noticeEntity(req.getBoardId())
	        .nttNo(req.getNoticeNo())
	        .build())
	        .build();
	    return commentRepository.getCommentList(pk);
	  }catch(Exception e) {
	    e.printStackTrace();
	  }
	  
	  return null;
	  
	}
	
	/** 댓글 생성 */
	@Transactional
	public List<CommentResponse> addComment (CommentRequest commentRequest){
		CommentEntity commentEntity = CommentEntity.builder()
		    .postEntity(PostEntity.builder()
		        .noticeEntity(NoticeEntity.builder()
		            .bbsId(commentRequest.getBoardId())
		            .build())
		        .nttNo(commentRequest.getNoticeNo())
		        .build())
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
		    .postEntity(PostEntity.builder()
            .noticeEntity(NoticeEntity.builder()
                .bbsId(commentRequest.getBoardId())
                .build())
            .nttNo(commentRequest.getNoticeNo())
            .build())
				.cmntNo(commentRequest.getCommentNo())
				.cmntCn(commentRequest.getCommentCn())
				.upperCmntNo(commentRequest.getUpperCommentNo())
				.build();
		commentRepository.save(commentEntity);
	}
	
	/** 임시 댓글 삭제 */ 
	@Transactional
	public void removeComment (CommentRequest commentRequest) {
		//commentRepository.deleteByCmntNo(commentRequest.getCommentNo());
		//commentRepository.deleteByUpperCmntNo(commentRequest.getCommentNo());
	}
}
