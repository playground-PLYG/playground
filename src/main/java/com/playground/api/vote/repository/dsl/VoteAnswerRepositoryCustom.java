package com.playground.api.vote.repository.dsl;

import java.util.List;
import com.playground.api.vote.entity.VoteAnswerEntity;
import com.playground.api.vote.model.VoteAnswerSubResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResultResponse;

public interface VoteAnswerRepositoryCustom {

  /**
   * 답변정보를 등록하기 전에 이미 등록된 정보 있는지 확인 하는 query
   */
  List<VoteAnswerEntity> getVoteAnswerEntityList(VoteAnswerEntity qestnAnswerEntity);

  /**
   * 내가 투표한 항목을 가져오는 Query
   */
  List<VoteAnswerSubResponse> getMyVoteAnswerList(VoteRequest reqData);

  /**
   * 투표결과를 질문에 따라서 가져오는 Query
   */
  List<VoteResultResponse> getVoteQestnResult(VoteRequest reqData);

  /**
   * 투표결과를 가져 올 때 투표한 유저들의 ID를 List로 가져오기
   */
  List<String> getAnswerUserIds(Integer voteSsno, Integer questionSsno, Integer itemSsno);
}
