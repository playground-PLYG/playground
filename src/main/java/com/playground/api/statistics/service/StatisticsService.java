package com.playground.api.statistics.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.statistics.model.StatisticsDetailDetailResponse;
import com.playground.api.statistics.model.StatisticsDetailResponse;
import com.playground.api.statistics.model.StatisticsRequest;
import com.playground.api.statistics.model.StatisticsResponse;
import com.playground.api.vote.repository.QestnAnswerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {
  private final QestnAnswerRepository qestnAnswerRepository;

  @Transactional(readOnly = true)
  public StatisticsResponse getVoteStatistics(StatisticsRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      StatisticsResponse statResponse = qestnAnswerRepository.selectVoteStatistics(reqData);
      List<StatisticsDetailResponse> detailList = qestnAnswerRepository.selectVoteDetailStatistics(reqData);
      statResponse.setStaDetailList(detailList);

      Integer voteNo = statResponse.getVoteSsno();
      if (!ObjectUtils.isEmpty(detailList)) {
        for (StatisticsDetailResponse detail : detailList) {
          Integer questionNo = detail.getQuestionSsno();
          for (StatisticsDetailDetailResponse ddetail : detail.getStaDetailDetailList()) {
            List<String> userIdList = new ArrayList<>();
            userIdList = qestnAnswerRepository.selectAnswerUserIds(voteNo, questionNo, ddetail.getItemSsno());
            ddetail.setSelUserIdList(userIdList);
          }
        }
      }
      return statResponse;
    } else {
      return new StatisticsResponse();
    }
  }
}
