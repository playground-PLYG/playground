package com.playground.api.vote.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.model.QestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteRstrntResponse;

public interface VoteRstrntRepositoryCustom {
  List<VoteRstrntResponse> getVoteRstrntList();
}
