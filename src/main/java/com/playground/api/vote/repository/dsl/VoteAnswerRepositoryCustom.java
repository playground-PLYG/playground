package com.playground.api.vote.repository.dsl;

import java.util.List;
import com.playground.api.statistics.model.StatisticsDetailResponse;
import com.playground.api.statistics.model.StatisticsRequest;
import com.playground.api.statistics.model.StatisticsResponse;
import com.playground.api.vote.entity.VoteAnswerEntity;

public interface VoteAnswerRepositoryCustom {
  List<VoteAnswerEntity> findBySsno(VoteAnswerEntity qestnAnswerEntity);

  Long deleteBySsno(Integer answerSsno);

  VoteAnswerEntity selectByEntity(VoteAnswerEntity qestnAnswerEntity);

  Long selectByAnswerUserId(Integer voteSsno, String answerUserId);

  /**
   * 통계에 사용하는 method
   */
  StatisticsResponse selectVoteStatistics(StatisticsRequest reqData);

  List<StatisticsDetailResponse> selectVoteDetailStatistics(StatisticsRequest reqData);

  List<String> selectAnswerUserIds(Integer voteSsno, Integer questionSsno, Integer itemSsno);
}
