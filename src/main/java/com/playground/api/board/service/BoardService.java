package com.playground.api.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.board.entity.CommentEntity;
import com.playground.api.board.entity.CommentEntityPK;
import com.playground.api.board.entity.NoticeEntity;
import com.playground.api.board.entity.PostEntity;
import com.playground.api.board.entity.PostEntityPK;
import com.playground.api.board.model.CommentRequest;
import com.playground.api.board.model.CommentResponse;
import com.playground.api.board.model.NoticeRequest;
import com.playground.api.board.model.NoticeResponse;
import com.playground.api.board.model.PostRequest;
import com.playground.api.board.model.PostResponse;
import com.playground.api.board.repository.CommentRepository;
import com.playground.api.board.repository.NoticeRepository;
import com.playground.api.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
  // private final NoticeEntity noticeEntity;
  private final NoticeRepository noticeRepository;

  private final CommentRepository commentRepository;

  private final PostRepository postRepository;

  /** 전체 게시판 목록 조회 */
  public List<NoticeResponse> getNoticeList() {
    List<NoticeEntity> noticeEntity = noticeRepository.findAll();
    return noticeEntity.stream().map(entity -> NoticeResponse.builder().boardId(entity.getBbsId()).boardNm(entity.getBbsNm()).build()).toList();
  }

  /** 게시판 생성 */
  public NoticeResponse addNotice(NoticeRequest noticeRequest) {
    NoticeEntity noticeEntity = NoticeEntity.builder().bbsId(noticeRequest.getBoardId()).bbsNm(noticeRequest.getBoardNm()).build();
    noticeRepository.save(noticeEntity);
    return NoticeResponse.builder().boardId(noticeEntity.getBbsId()).boardNm(noticeEntity.getBbsNm()).build();
  }

  /** 게시판 삭제 */
  public NoticeResponse removeNotice(NoticeRequest noticeRequest) {
    noticeRepository.deleteById(noticeRequest.getBoardId());
    return NoticeResponse.builder().boardId(noticeRequest.getBoardId()).boardNm(noticeRequest.getBoardNm()).build();
  }

  /** 전체 게시물 목록 조회 */
  @Transactional(readOnly = true)
  public Page<PostResponse> getPostList(Pageable pageable, PostRequest req) {

    PostEntity schEntity = PostEntity.builder().noticeEntity(NoticeEntity.builder().bbsId(req.getBoardId()).build()).nttSj(req.getNoticeSj()).build();

    Page<PostEntity> postPageList = postRepository.getPostList(schEntity, pageable);

    List<PostResponse> postList = postPageList.stream()
        .map(entity -> PostResponse.builder().noticeNo(entity.getNttSn()).boardId(entity.getNoticeEntity().getBbsId()).noticeSj(entity.getNttSj())
            .noticeCn(entity.getNttCn()).registUsrId(entity.getRegistUsrId()).registDt(entity.getRegistDt()).updtUsrId(entity.getUpdtUsrId())
            .updtDt(entity.getUpdtDt()).build())
        .toList();

    return new PageImpl<>(postList, postPageList.getPageable(), postPageList.getTotalElements());

  }

  /** 게시물 생성 */
  @Transactional
  public PostResponse addPost(PostRequest postRequest) {
    PostEntity postEntity = PostEntity.builder().noticeEntity(NoticeEntity.builder().bbsId(postRequest.getBoardId()).build())
        .nttSj(postRequest.getNoticeSj()).nttCn(postRequest.getNoticeCn()).build();
    postRepository.save(postEntity);

    return PostResponse.builder().boardId(postEntity.getNoticeEntity().getBbsId()).noticeSj(postEntity.getNttSj()).noticeCn(postEntity.getNttCn())
        .registUsrId(postEntity.getRegistUsrId()).updtUsrId(postEntity.getUpdtUsrId()).build();
  }

  /** 게시물 수정 */
  @Transactional
  public PostResponse modifyPost(PostRequest postRequest) {
    PostEntity postEntity =
        PostEntity.builder().nttSn(postRequest.getNoticeNo()).noticeEntity(NoticeEntity.builder().bbsId(postRequest.getBoardId()).build())
            .nttSj(postRequest.getNoticeSj()).nttCn(postRequest.getNoticeCn()).build();
    postRepository.save(postEntity);

    return PostResponse.builder().noticeNo(postEntity.getNttSn()).boardId(postEntity.getNoticeEntity().getBbsId()).noticeSj(postEntity.getNttSj())
        .noticeCn(postEntity.getNttCn()).registUsrId(postEntity.getRegistUsrId()).updtUsrId(postEntity.getUpdtUsrId()).build();
  }


  /** 게시판 삭제 */
  @Transactional
  public void removePost(PostRequest postRequest) {
    // postRepository.deleteByNttNo(postRequest.getNttNo());
  }

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
          .commentCn(commentEntity.getCmntCn()).upperCommentNo(commentEntity.getUpperCmntSn()).deleteChk(commentEntity.getDeleteAt())
          .registUsrId(commentEntity.getRegistUsrId()).build();

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
    CommentEntity.CommentEntityBuilder commentEntityBuilder =
        CommentEntity.builder().postEntity(PostEntity.builder().noticeEntity(NoticeEntity.builder().bbsId(commentRequest.getBoardId()).build())
            .nttSn(commentRequest.getNoticeNo()).build()).cmntCn(commentRequest.getCommentCn());

    if (commentRequest.getUpperCommentNo() > 0) {
      commentEntityBuilder.upperCmntSn(commentRequest.getUpperCommentNo());
    }

    CommentEntity commentEntity = commentEntityBuilder.build();

    commentRepository.save(commentEntity);

    return null;
  }

  /** 댓글 수정 */
  @Transactional
  public void modifyComment(CommentRequest commentRequest) {
    CommentEntity commentEntity = commentRepository.findByCmntSn(commentRequest.getCommentNo());

    CommentEntity updateCommentEntity = CommentEntity.builder().cmntSn(commentEntity.getCmntSn()).postEntity(commentEntity.getPostEntity())
        .cmntCn(commentRequest.getCommentCn()).upperCmntSn(commentEntity.getUpperCmntSn()).deleteAt(commentEntity.getDeleteAt()).build();

    commentRepository.save(updateCommentEntity);
  }

  /** 임시 댓글 삭제 */
  @Transactional
  public void removeComment(CommentRequest commentRequest) {
    CommentEntity commentEntity = commentRepository.findByCmntSn(commentRequest.getCommentNo());

    CommentEntity deleteCommentEntity = CommentEntity.builder().cmntSn(commentEntity.getCmntSn()).postEntity(commentEntity.getPostEntity())
        .cmntCn(commentEntity.getCmntCn()).upperCmntSn(commentEntity.getUpperCmntSn()).deleteAt("Y").build();

    commentRepository.save(deleteCommentEntity);
  }

}
