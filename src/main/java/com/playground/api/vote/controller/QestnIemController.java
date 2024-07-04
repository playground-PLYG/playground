package com.playground.api.vote.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.vote.model.VoteQestnIemRequest;
import com.playground.api.vote.model.VoteQestnIemResponse;
import com.playground.api.vote.model.VoteQestnRequest;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.service.QestnIemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "QestnIem", description = "질문과 항목을 관리하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class QestnIemController {
  private final QestnIemService qestnIemService;

  /**
   * 질문등록
   */
  @Operation(summary = "질문등록", description = "관리자가 질문을 등록")
  @PostMapping("/api/qestn/addQestn")
  public List<VoteQestnResponse> addQestn(@RequestBody @Valid List<VoteQestnRequest> qestnReqList) {
    return qestnIemService.addQestn(qestnReqList);
  }

  /**
   * 질문수정
   */
  @Operation(summary = "질문수정", description = "관리자가 질문을 수정")
  @PutMapping("/api/qestn/modifyQestn")
  public List<VoteQestnResponse> modifyQestn(@RequestBody @Valid List<VoteQestnRequest> qestnReqList) {
    return qestnIemService.modifyQestn(qestnReqList);
  }

  /**
   * 질문삭제
   */
  @Operation(summary = "질문삭제", description = "관리자가 질문을 삭제")
  @DeleteMapping("/api/qestn/removeQestn")
  public Long removeQestn(@RequestBody @Valid VoteQestnRequest qestnRequest) {
    return qestnIemService.removeQestn(qestnRequest);
  }

  /**
   * 투표항목등록
   */
  @Operation(summary = "투표항목등록", description = "관리자가 투표항목을 등록")
  @PostMapping("/api/voteIem/addVoteIem")
  public List<VoteQestnIemResponse> addVoteIem(@RequestBody @Valid List<VoteQestnIemRequest> voteIemReqList) {
    return qestnIemService.addVoteIem(voteIemReqList);
  }

  /**
   * 투표항목수정
   */
  @Operation(summary = "투표항목수정", description = "관리자가 투표항목을 수정")
  @PutMapping("/api/voteIem/modifyVoteIem")
  public List<VoteQestnIemResponse> modifyVoteIem(@RequestBody @Valid List<VoteQestnIemRequest> voteIemReqList) {
    return qestnIemService.modifyVoteIem(voteIemReqList);
  }

  /**
   * 투표항목삭제
   */
  @Operation(summary = "투표항목삭제", description = "관리자가 투표항목을 삭제")
  @DeleteMapping("/api/voteIem/removeVoteIem")
  public Long removeVoteIem(@RequestBody @Valid VoteQestnIemRequest voteIemRequest) {
    return qestnIemService.removeVoteIem(voteIemRequest);
  }

}
