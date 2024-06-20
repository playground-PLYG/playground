package com.playground.api.statistics.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.statistics.model.StatisticsRequest;
import com.playground.api.statistics.model.StatisticsResponse;
import com.playground.api.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Statistics", description = "투표결과의 통계를 관리하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class StatisticsController {
  private final StatisticsService statisticsService;

  /**
   * 통계 조회
   */
  @Operation(summary = "통계 조회", description = "투표에 대한 통계")
  @PostMapping("/public/statistics/getVoteStatistics")
  public StatisticsResponse getVoteStatistics(@RequestBody @Valid StatisticsRequest reqData) {
    return statisticsService.getVoteStatistics(reqData);
  }
}
