package com.playground.api.vote.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "vote", description = "투표를 관리하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
@Slf4j
public class VoteController {
  private final VoteService voteService;

  /**
   * 투표목록 조회
   */
  @Operation(summary = "투표목록 조회", description = "투표목록을 검색조건에 의해서 검색하고, 페이징처리를 하여 화면에 노출")
  @PostMapping("/api/vote/getVotePageList")
  public Page<VoteResponse> getVotePageList(@RequestBody @Valid VoteRequest reqData, Pageable pageable) {
    return voteService.getVotePageList(reqData, pageable);
  }

  /**
   * 투표내용 상세조회
   */
  @Operation(summary = "투표내용 상세조회", description = "투표 정보를 상세하게 조회")
  @PostMapping("/api/vote/getVoteDetail")
  public VoteResponse getVoteDetail(@RequestBody @Valid VoteRequest reqData) {
    return voteService.getVoteDetail(reqData);
  }

  /**
   * 투표등록
   */
  @Operation(summary = "투표등록", description = "관리자가 투표를 등록")
  @PostMapping("/api/vote/addVote")
  public VoteResponse addVote(@RequestBody @Valid VoteRequest reqData) {
    return voteService.addVote(reqData);
  }

  /**
   * 투표수정
   */
  @Operation(summary = "투표수정", description = "관리자가 투표를 수정")
  @PutMapping("/api/vote/modifyVote")
  public VoteResponse modifyVote(@RequestBody @Valid VoteRequest reqData) {
    log.debug("##### ##### modifyVote reqData : {}", reqData);
    return voteService.modifyVote(reqData);
  }

  /**
   * 투표삭제
   */
  @Operation(summary = "투표삭제", description = "관리자가 투표를 삭제")
  @DeleteMapping("/api/vote/removeVote")
  public VoteResponse removeVote(@RequestBody @Valid VoteRequest reqData) {
    return voteService.removeVote(reqData);
  }

}
