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

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;

  /** 댓글 조회 */
  @Transactional(readOnly = true)
  public List<CommentResponse> getCommentList(CommentRequest reqData) {
    CommentEntityPK pk =
        CommentEntityPK.builder().postEntity(PostEntityPK.builder().noticeEntity(reqData.getBoardId()).nttSn(reqData.getNoticeNo()).build()).build();

    List<CommentEntity> commentEntityList = commentRepository.getCommentList(pk);
    List<CommentResponse> res = new ArrayList<>();
    Map<String, CommentResponse> tempCommentResponseMap = new HashMap<>();

    commentEntityList.stream().forEach(commentEntity -> {
      CommentResponse commentResponse = CommentResponse.builder().commentNo(commentEntity.getCmntSn())
          .noticeNo(commentEntity.getPostEntity().getNttSn()).boardId(commentEntity.getPostEntity().getNoticeEntity().getBbsId())
          .commentCn(commentEntity.getCmntCn()).upperCommentNo(commentEntity.getUpperCmntSn()).build();

      String currentId = String.format("%s_%s_%s", commentResponse.getBoardId(), commentResponse.getNoticeNo(), commentResponse.getCommentNo());

      tempCommentResponseMap.put(currentId, commentResponse);

      if (commentEntity.getParent() != null) {
        String parentId = String.format("%s_%s_%s", commentResponse.getBoardId(), commentResponse.getNoticeNo(), commentResponse.getUpperCommentNo());

        tempCommentResponseMap.get(parentId).getCommentList().add(commentResponse);
      } else {
        res.add(commentResponse);
      }
    });

    return res;
  }

  /** 댓글 생성 */
  @Transactional
  public List<CommentResponse> addComment(CommentRequest commentRequest) {
    CommentEntity commentEntity = CommentEntity.builder()
        .postEntity(PostEntity.builder().noticeEntity(NoticeEntity.builder().bbsId(commentRequest.getBoardId()).build())
            .nttSn(commentRequest.getNoticeNo()).build())
        .cmntCn(commentRequest.getCommentCn()).upperCmntSn(commentRequest.getUpperCommentNo()).build();
    commentRepository.save(commentEntity);

    return null;
  }

  /** 댓글 수정 */
  @Transactional
  public void modifyComment(CommentRequest commentRequest) {
    CommentEntity commentEntity = CommentEntity.builder()
        .postEntity(PostEntity.builder().noticeEntity(NoticeEntity.builder().bbsId(commentRequest.getBoardId()).build())
            .nttSn(commentRequest.getNoticeNo()).build())
        .cmntSn(commentRequest.getCommentNo()).cmntCn(commentRequest.getCommentCn()).upperCmntSn(commentRequest.getUpperCommentNo()).build();
    commentRepository.save(commentEntity);
  }

  /** 임시 댓글 삭제 */
  @Transactional
  public void removeComment(CommentRequest commentRequest) {
    // commentRepository.deleteByCmntNo(commentRequest.getCommentNo());
    // commentRepository.deleteByUpperCmntNo(commentRequest.getCommentNo());
  }
}
