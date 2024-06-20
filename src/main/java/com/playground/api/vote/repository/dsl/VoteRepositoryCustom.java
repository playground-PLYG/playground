package com.playground.api.vote.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.model.QestnResponse;
import com.playground.api.vote.model.VoteRequest;

public interface VoteRepositoryCustom {
  Page<VoteEntity> getVotePageList(VoteRequest reqData, Pageable pageable);

  List<QestnResponse> getQestnDetail(Integer voteSsno, Integer questionSsno);

  List<QestnEntity> getQestnList(Integer voteSsno);

  Long updateByIdForVote(VoteRequest reqData);

  Long deleteByVoteSnForQestn(Integer voteSsno);

  Long deleteBySsnoForQestn(Integer voteSsno, Integer questionSsno);

  Long deleteByVoteSnForVoteIem(Integer voteSsno);

  Long deleteBySsnoForVoteIem(Integer voteSsno, Integer questionSsno);

}
