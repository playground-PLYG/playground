package com.playground.api.vote.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteSrchRequest;
import com.playground.api.vote.model.VoteSrchResponse;

public interface VoteRepositoryCustom {

  List<VoteQestnResponse> getVoteQestnDetail(Integer voteSsno);

  Page<VoteSrchResponse> getVotePageList(VoteSrchRequest reqData, Pageable pageable);
}
