package com.playground.api.vote.repository.dsl;

import java.util.List;
import com.playground.api.vote.model.VoteRstrntResponse;

public interface VoteRstrntRepositoryCustom {
  
  Integer getVoteRstrntCount();
  
  List<VoteRstrntResponse> getVoteRstrntList(); //당일 점심 투표 목록 조회
}
