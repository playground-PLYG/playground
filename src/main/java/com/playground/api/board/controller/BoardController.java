package com.playground.api.board.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.board.model.CommentRequest;
import com.playground.api.board.model.CommentResponse;
import com.playground.api.board.model.NoticeRequest;
import com.playground.api.board.model.NoticeResponse;
import com.playground.api.board.model.PostRequest;
import com.playground.api.board.model.PostResponse;
import com.playground.api.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "board", description = "게시판")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class BoardController {
  private final BoardService boardService;

  /**
   * 게시판 목록 조회
   *
   */
  @Operation(summary = "게시판 목록 조회", description = "게시판 목록 조회")
  @GetMapping("/public/notice/getNoticeList")
  public List<NoticeResponse> getNoticeList() {
    return boardService.getNoticeList();
  }

  /**
   * 게시판 생성
   *
   */
  @Operation(summary = "게시판 생성", description = "게시판 생성")
  @PostMapping("/api/notice/addNotice")
  public NoticeResponse addNotice(@RequestBody NoticeRequest noticeRequest) {
    return boardService.addNotice(noticeRequest);
  }

  /**
   * 게시판 삭제
   *
   */
  @Operation(summary = "게시판 삭제", description = "게시판 삭제")
  @DeleteMapping("/api/notice/removeNotice")
  public NoticeResponse removeNotice(@RequestBody NoticeRequest noticeRequest) {
    return boardService.removeNotice(noticeRequest);
  }

  /**
   * 게시물 목록 조회
   *
   */
  @Operation(summary = "게시물 전체 조회", description = "게시물 조회")
  @PostMapping("/public/post/getPostList")
  public List<PostResponse> getPostList(@RequestBody PostRequest req) {
    return boardService.getPostList(req);
  }

  /**
   * 게시물 목록 생성
   *
   */
  @Operation(summary = "게시물 생성", description = "게시물 생성")
  @PostMapping("/api/post/addPost")
  public PostResponse addPost(@RequestBody PostRequest postRequest) {

    return boardService.addPost(postRequest);
  }

  @Operation(summary = "게시물 수정", description = "게시물 수정")
  @PostMapping("/api/post/modifyPost")
  public PostResponse modifyPost(@RequestBody PostRequest postRequest) {

    return boardService.modifyPost(postRequest);
  }

  /**
   * 게시물 삭제
   *
   */
  @Operation(summary = "게시판 삭제", description = "게시판 삭제")
  @DeleteMapping("/api/post/removePost")
  public void removePost(@RequestBody PostRequest postRequest) {
    boardService.removePost(postRequest);
  }

  /**
   * 댓글 조회
   */
  @Operation(summary = "댓글 조회", description = "댓글 조회")
  @PostMapping("/public/comment/getCommentList")
  public List<CommentResponse> getCommentList(@RequestBody CommentRequest req) {
    return boardService.getCommentList(req);
  }

  /**
   * 댓글저장
   */
  @Operation(summary = "댓글저장", description = "댓글저장")
  @PostMapping("/api/comment/addComment")
  public List<CommentResponse> addComment(@RequestBody CommentRequest commentRequest) {
    return boardService.addComment(commentRequest);
  }

  /**
   * 댓들 수정
   */
  @Operation(summary = "댓글수정", description = "댓글수정")
  @PostMapping("/api/comment/modifyComment")
  public void modifyComment(@RequestBody CommentRequest commentRequest) {
    boardService.modifyComment(commentRequest);
  }

  /*
   * 댓글삭제
   */
  @Operation(summary = "댓글삭제", description = "댓글삭제")
  @DeleteMapping("/api/comment/removeComment")
  public void removeComment(@RequestBody CommentRequest commentRequest) {
    boardService.removeComment(commentRequest);
  }
}
