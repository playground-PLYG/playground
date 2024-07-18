package com.playground.api.vote.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.vote.model.VoteAddRequest;
import com.playground.api.vote.model.VoteAddResponse;
import com.playground.api.vote.model.VoteAnswerRequest;
import com.playground.api.vote.model.VoteAnswerResponse;
import com.playground.api.vote.model.VoteModifyRequest;
import com.playground.api.vote.model.VoteModifyResponse;
import com.playground.api.vote.model.VoteQestnIemRequest;
import com.playground.api.vote.model.VoteQestnIemResponse;
import com.playground.api.vote.model.VoteQestnRequest;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.model.VoteRstrntResponse;
import com.playground.api.vote.model.VoteSrchRequest;
import com.playground.api.vote.model.VoteSrchResponse;
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
  @PostMapping("/public/vote/getVoteList")
  public Page<VoteSrchResponse> getVoteList(@RequestBody @Valid VoteSrchRequest reqData, Pageable pageable) {
    return voteService.getVoteList(reqData, pageable);
  }

  /**
   * 투표등록
   */
  @Operation(summary = "투표등록", description = "관리자가 투표를 등록")
  @PostMapping("/api/vote/addVote")
  public VoteAddResponse addVote(@RequestBody @Valid VoteAddRequest reqData) {
    return voteService.addVote(reqData);
  }

  /**
   * 투표수정
   */
  @Operation(summary = "투표수정", description = "관리자가 투표를 수정")
  @PutMapping("/api/vote/modifyVote")
  public VoteModifyResponse modifyVote(@RequestBody @Valid VoteModifyRequest reqData) {
    return voteService.modifyVote(reqData);
  }


  /**
   * 투표내용 상세조회 (Edited by.PSJ, End date.2024.07.08)
   */
  @Operation(summary = "투표내용 상세조회", description = "투표 정보를 상세하게 조회")
  @PostMapping("/api/vote/getVoteDetail")
  public VoteResponse getVoteDetail(@RequestBody @Valid VoteRequest reqData) {
    return voteService.getVoteDetail(reqData);
  }

  /**
   * 투표의 답변 등록 하기 (Edited by.PSJ, End date.2024.07.08)
   */
  @Operation(summary = "투표답변등록", description = "답변정보를 등록하는 api")
  @PostMapping("/api/vote/addVoteAnswer")
  public List<VoteAnswerResponse> addVoteAnswer(@RequestBody @Valid List<VoteAnswerRequest> reqDataList) {
    return voteService.addVoteAnswer(reqDataList);
  }

  /**
   * 내 투표 조회 (Edited by.PSJ, End date.2024.07.15)
   */
  @Operation(summary = "내투표조회", description = "내가 투표한 항목을 가져오는 API")
  @PostMapping("/api/vote/getMyVote")
  public VoteAnswerResponse getMyVote(@RequestBody @Valid VoteRequest reqData) {
    return voteService.getMyVote(reqData);
  }

  /**
   * 투표결과 상세 보기 (Edited by.PSJ, End date.2024.07.15)
   */
  @Operation(summary = "결과상세보기", description = "투표한 결과를 상세하게 조회")
  @PostMapping("/api/vote/getVoteResult")
  public VoteResponse getVoteResult(@RequestBody @Valid VoteRequest reqData) {
    return voteService.getVoteResult(reqData);
  }

  /////////////////////////////////////////////////////////////////////////////
  //////////////// 이하 메소드는 개발완료 후 삭제 할 예정 참고 만 하기 ////////////////////////
  /////////////////////////////////////////////////////////////////////////////


  /**
   * 투표삭제
   */
  @Operation(summary = "투표삭제", description = "관리자가 투표를 삭제")
  @DeleteMapping("/api/vote/removeVote")
  public VoteResponse removeVote(@RequestBody @Valid VoteRequest reqData) {
    return voteService.removeVote(reqData);
  }

  /**
   * 질문등록
   */
  @Operation(summary = "질문등록", description = "관리자가 질문을 등록")
  @PostMapping("/api/qestn/addQestn")
  public List<VoteQestnResponse> addQestn(@RequestBody @Valid List<VoteQestnRequest> qestnReqList) {
    return voteService.addQestn(qestnReqList);
  }

  /**
   * 질문수정
   */
  @Operation(summary = "질문수정", description = "관리자가 질문을 수정")
  @PutMapping("/api/qestn/modifyQestn")
  public List<VoteQestnResponse> modifyQestn(@RequestBody @Valid List<VoteQestnRequest> qestnReqList) {
    return voteService.modifyQestn(qestnReqList);
  }

  /**
   * 질문삭제
   */
  @Operation(summary = "질문삭제", description = "관리자가 질문을 삭제")
  @DeleteMapping("/api/qestn/removeQestn")
  public Long removeQestn(@RequestBody @Valid VoteQestnRequest qestnRequest) {
    return voteService.removeQestn(qestnRequest);
  }

  /**
   * 투표항목등록
   */
  @Operation(summary = "투표항목등록", description = "관리자가 투표항목을 등록")
  @PostMapping("/api/voteIem/addVoteIem")
  public List<VoteQestnIemResponse> addVoteIem(@RequestBody @Valid List<VoteQestnIemRequest> voteIemReqList) {
    return voteService.addVoteIem(voteIemReqList);
  }

  /**
   * 투표항목수정
   */
  @Operation(summary = "투표항목수정", description = "관리자가 투표항목을 수정")
  @PutMapping("/api/voteIem/modifyVoteIem")
  public List<VoteQestnIemResponse> modifyVoteIem(@RequestBody @Valid List<VoteQestnIemRequest> voteIemReqList) {
    return voteService.modifyVoteIem(voteIemReqList);
  }

  /**
   * 투표항목삭제
   */
  @Operation(summary = "투표항목삭제", description = "관리자가 투표항목을 삭제")
  @DeleteMapping("/api/voteIem/removeVoteIem")
  public Long removeVoteIem(@RequestBody @Valid VoteQestnIemRequest voteIemRequest) {
    return voteService.removeVoteIem(voteIemRequest);
  }

  /**
   * 투표 내용 조회하기
   */
  @Operation(summary = "투표조회", description = "투표내용을 조회")
  @PostMapping("/public/voteAnswer/getVoteDetail")
  public VoteResponse getVoteDetailOnAnswer(@RequestBody @Valid VoteRequest reqData) {
    return voteService.getVoteDetailOnAnswer(reqData);
  }

  /**
   * 답변 조회 하기
   */
  @Operation(summary = "답변조회", description = "답변정보를 조회")
  @PostMapping("/public/voteAnswer/getAnswer")
  public List<VoteAnswerResponse> getAnswer(@RequestBody @Valid VoteAnswerRequest reqData) {
    log.debug("############## request ::: {}", reqData);
    return voteService.getAnswer(reqData);
  }


  /**
   * 당일 점심투표 등록
   */
  @Operation(summary = "당일 점심투표 등록", description = "하루한번 자동으로 점심투표를 생성할 API")
  @PostMapping("/api/voteRstrnt/addTodayLunchVote")
  public void addTodayLunchVote() {
    voteService.addTodayLunchVote();
  }

  /**
   * 당일 점심투표 조회
   */
  @Operation(summary = "당일 점심투표 조회", description = "금일 점심투표할 리스트 조회")
  @PostMapping("/api/voteRstrnt/getVoteRstrntList")
  public List<VoteRstrntResponse> getVoteRstrntList() {
    return voteService.getVoteRstrntList();
  }

}
