package com.playground.api.notice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
	
	/** 댓글 조회 */
	@Transactional(readOnly = true)
	public List<CommentResponse> getCommentList (CommentRequest req){
	    
	  try {
	    CommentEntityPK pk = CommentEntityPK.builder().postEntity(
          PostEntityPK.builder()
          .noticeEntity(req.getBoardId())
          .nttSn(req.getNoticeNo())
          .build())
      .build();
	    
	    List<CommentEntity> comments = commentRepository.getCommentList(pk);
	    List<CommentResponse> res = new ArrayList<>();
      Map<String, CommentResponse> map = new HashMap<>();
      comments.stream().forEach(c -> {
          log.debug("res :: {}", res);
          log.debug("map :: {}", map);
          CommentResponse dto = CommentResponse.builder()
              .commentNo(c.getCmntSn())
              .noticeNo(c.getPostEntity().getNttSn())
              .boardId(c.getPostEntity().getNoticeEntity().getBbsId())
              .commentCn(c.getCmntCn())
              .upperCommentNo(c.getUpperCmntSn())
              .build();
          
          log.debug("dto :: {}", dto);
          map.put(dto.getBoardId() +"_"+ dto.getNoticeNo() +"_"+ dto.getCommentNo(), dto);
          if(c.getParent() != null) {
            map.get(c.getParent().getPostEntity().getNoticeEntity().getBbsId()+"_"+c.getParent().getPostEntity().getNttSn()+"_"+c.getCmntSn()).getCommentList().add(dto);
          }
          else {
            res.add(dto);
          }
      });
	    
      return res;
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
		        .nttSn(commentRequest.getNoticeNo())
		        .build())
				.cmntCn(commentRequest.getCommentCn())
				.upperCmntSn(commentRequest.getUpperCommentNo())
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
            .nttSn(commentRequest.getNoticeNo())
            .build())
				.cmntSn(commentRequest.getCommentNo())
				.cmntCn(commentRequest.getCommentCn())
				.upperCmntSn(commentRequest.getUpperCommentNo())
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
