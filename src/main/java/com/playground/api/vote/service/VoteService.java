package com.playground.api.vote.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {
  private final VoteRepository voteRepository;
  private final ModelMapper modelMapper;
  
  @Transactional(readOnly = true)
  public Page<VoteResponse> getVotePageList(VoteRequest reqData, Pageable pageable) {
    try {
      Page<VoteEntity> votePageList = voteRepository.getVotePageList(reqData, pageable);
      List<VoteResponse> voteList = votePageList.getContent().stream()
        .map(entity -> VoteResponse.builder().voteSsno(entity.getVoteSn()).voteKindCode(entity.getVoteKndCode()).voteSubject(entity.getVoteSj())
            .anonymityVoteAlternative(entity.getAnnymtyVoteAt()).voteBeginDate(entity.getVoteBeginDt()).voteEndDate(entity.getVoteEndDt())
            .voteDeleteAlternative(entity.getVoteDeleteAt()).build())
        .toList();
      return new PageImpl<>(voteList, votePageList.getPageable(), votePageList.getTotalElements());
    }catch(Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

  @Transactional(readOnly = true)
  public List<VoteResponse> getVoteDetail(VoteRequest reqData) {
    try {
      List<VoteResponse> voteDetailList = voteRepository.getVoteDetail(reqData);
      log.debug("voteDetailList : {}", voteDetailList);
      
      return voteDetailList;
    }catch(Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public VoteResponse addVote(VoteRequest reqData) {



    String tempExcuteResult = "1";
    return VoteResponse.builder().excuteResult(tempExcuteResult).build();
  }

  public VoteResponse modifyVote(VoteRequest reqData) {



    String tempExcuteResult = "1";
    return VoteResponse.builder().excuteResult(tempExcuteResult).build();
  }

  public VoteResponse removeVote(VoteRequest reqData) {



    String tempExcuteResult = "1";
    return VoteResponse.builder().excuteResult(tempExcuteResult).build();
  }
}
