package com.playground.api.vote.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;

public interface VoteRepositoryCustom {
  Page<VoteEntity> getVotePageList(VoteRequest reqData, Pageable pageable);

  List<VoteResponse> getVoteDetail(VoteRequest reqData);

}
