package com.playground.api.vote.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.entity.VoteQestnEntity;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteSrchRequest;

public interface VoteRepositoryCustom {

  List<VoteQestnResponse> getVoteQestnDetail(Integer voteSsno);

  Page<VoteEntity> getVotePageList(VoteSrchRequest reqData, Pageable pageable);

  /////////////////////////////////////////////////////////////////////////////////
  ////////////////////// 이하 메소드 사용하는거는 위로 올릴 것 개발 완료후 삭제 예정/////////////////


  List<VoteQestnEntity> getQestnList(Integer voteSsno);

  Long updateByIdForVote(VoteRequest reqData);

  Long deleteByVoteSnForQestn(Integer voteSsno);

  Long deleteBySsnoForQestn(Integer voteSsno, Integer questionSsno);

  Long deleteByVoteSnForVoteIem(Integer voteSsno);

  Long deleteBySsnoForVoteIem(Integer voteSsno, Integer questionSsno);

}
