package com.playground.api.vote.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.vote.model.VoteRstrntResponse;
import com.playground.api.vote.service.VoteRstrntService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "voteRstrnt", description = "식당메뉴투표를 관리하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
@Slf4j
public class VoteRstrntController {  
  private final VoteRstrntService voteRstrntService;
  /**
   * 당일 점심투표 등록
   */
  @Operation(summary = "당일 점심투표 등록", description = "하루한번 자동으로 점심투표를 생성할 API")
  @PostMapping("/api/voteRstrnt/addTodayLunchVote")
  public void addTodayLunchVote() {
    voteRstrntService.addTodayLunchVote();
  }
  
  /**
   * 당일 점심투표 조회
   */
  @Operation(summary = "당일 점심투표 조회", description = "금일 점심투표할 리스트 조회")
  @PostMapping("/api/voteRstrnt/getVoteRstrntList")
  public List<VoteRstrntResponse> getVoteRstrntList() {
    return voteRstrntService.getVoteRstrntList();
  }

}
