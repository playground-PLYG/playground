package com.playground.api.vote.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.vote.model.QestnAnswerRequest;
import com.playground.api.vote.model.QestnAnswerResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "answer", description = "투표에 대한 답변을 관리하는 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
@Slf4j
public class AnswerController {
  private final AnswerService answerService;
  
  /**
   * 중복투표 여부 조회 --> memberId로 조회
   * */
  @Operation(summary = "중복투표여부 조회", description = "회원아이디로 검색해서 투표가 중복인지 여부 조회")
  @PostMapping("/public/voteAnswer/isDuplicateVote")
  public Boolean isDuplicateVote(@RequestBody @Valid QestnAnswerRequest reqData) {
    return answerService.isDuplicateVote(reqData);
  }
  
  /**
   * 투표 내용 조회하기
   * */
  @Operation(summary = "투표조회", description = "투표내용을 조회")
  @PostMapping("/public/voteAnswer/getVoteDetail")
  public VoteResponse getVoteDetail(@RequestBody @Valid VoteRequest reqData) {
    return answerService.getVoteDetail(reqData);
  }
  
  /**
   * 답변 조회 하기
   */
  @Operation(summary = "답변조회", description = "답변정보를 조회")
  @PostMapping("/public/voteAnswer/getAnswer")
  public List<QestnAnswerResponse> getAnswer(@RequestBody @Valid QestnAnswerRequest reqData) {
    log.debug("############## request ::: {}", reqData);
    return answerService.getAnswer(reqData);
  }
  
  /**
   * 답변 등록 하기
   */
  @Operation(summary = "답변등록", description = "답변정보를 등록하는 api")
  @PostMapping("/public/voteAnswer/addAnswer")
  public List<QestnAnswerResponse> addAnswer(@RequestBody @Valid List<QestnAnswerRequest> reqDataList) {
    return answerService.addAnswer(reqDataList);
  }
  
  /**
   * 답변수정
   */
  @Operation(summary = "답변수정", description = "사용자가 답변을 수정")
  @PutMapping("/public/voteAnswer/modifyAnswer")
  public List<QestnAnswerResponse> modifyAnswer(@RequestBody @Valid List<QestnAnswerRequest> reqDataList) {
    return answerService.modifyAnswer(reqDataList);
  }

  /**
   * 답변삭제
   */
  @Operation(summary = "답변삭제", description = "사용자가 답변을 삭제..? 해당 API 는 사용 안할 것 같음")
  @DeleteMapping("/public/voteAnswer/removeAnswer")
  public Long removeAnswer(@RequestBody @Valid QestnAnswerRequest qestnAnswerRequest) {
    return answerService.removeAnswer(qestnAnswerRequest);
  }
  
}
