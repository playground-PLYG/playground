package com.playground.api.vote.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteIemEntity;
import com.playground.api.vote.model.QestnRequest;
import com.playground.api.vote.model.QestnResponse;
import com.playground.api.vote.model.VoteIemRequest;
import com.playground.api.vote.model.VoteIemResponse;
import com.playground.api.vote.repository.QestnRepository;
import com.playground.api.vote.repository.VoteIemRepository;
import com.playground.api.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QestnIemService {
  private final VoteRepository voteRepository;
  private final QestnRepository qestnRepository;
  private final VoteIemRepository voteIemRepository;
  private final ModelMapper modelMapper;

  @Transactional
  public List<QestnResponse> addQestn(List<QestnRequest> qestnReqList) {
    List<QestnEntity> resEntityList = new ArrayList<>();
    for(QestnRequest qestn : qestnReqList) {
      resEntityList.add(QestnEntity.builder()
          .voteSn(qestn.getVoteSsno())
          .qestnCn(qestn.getQuestionContents())
          .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative())
          .build());
    }
    
    List<QestnEntity> saveAllEntities = qestnRepository.saveAll(resEntityList);
     return saveAllEntities.stream().map(entity -> QestnResponse.builder()
         .questionSsno(entity.getQestnSn())
         .voteSsno(entity.getVoteSn())
         .questionContents(entity.getQestnCn())
         .compoundNumberChoiceAlternative(entity.getCompnoChoiseAt())
         .build())
         .toList();
  }

  @Transactional
  public List<QestnResponse> modifyQestn(List<QestnRequest> qestnReqList) {
    List<QestnEntity> resEntityList = new ArrayList<>();
    for(QestnRequest qestn : qestnReqList) {
      QestnEntity resQestn = qestnRepository.save(QestnEntity.builder()
          .qestnSn(qestn.getQuestionSsno())
          .voteSn(qestn.getVoteSsno())
          .qestnCn(qestn.getQuestionContents())
          .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative())
          .build());

      resEntityList.add(resQestn);
    }

     return resEntityList.stream().map(entity -> QestnResponse.builder()
         .questionSsno(entity.getQestnSn())
         .voteSsno(entity.getVoteSn())
         .questionContents(entity.getQestnCn())
         .compoundNumberChoiceAlternative(entity.getCompnoChoiseAt())
         .build())
         .toList();
  }

  @Transactional
  public Long removeQestn(QestnRequest qestnRequest) {
    if (!ObjectUtils.isEmpty(qestnRequest.getVoteSsno())) {
      return voteRepository.deleteByVoteSnForQestn(qestnRequest.getVoteSsno());
    }else {
      return 0L;
    }
  }

  @Transactional
  public List<VoteIemResponse> addVoteIem(List<VoteIemRequest> voteIemReqList) {
    List<VoteIemEntity> resEntityList = new ArrayList<>();
    for(VoteIemRequest voteIem : voteIemReqList) {
      resEntityList.add(VoteIemEntity.builder()
          .voteSn(voteIem.getVoteSsno())
          .qestnSn(voteIem.getQuestionSsno())
          .iemSn(voteIem.getItemSsno())
          .iemNm(voteIem.getItemName())
          .build());
    }
    
    List<VoteIemEntity> saveAllEntities = voteIemRepository.saveAll(resEntityList);
    return saveAllEntities.stream().map(entity -> VoteIemResponse.builder()
        .voteSsno(entity.getVoteSn())
        .questionSsno(entity.getQestnSn())
        .itemSsno(entity.getIemSn())
        .itemName(entity.getIemNm())
        .build())
        .toList();
  }

  @Transactional
  public List<VoteIemResponse> modifyVoteIem(List<VoteIemRequest> voteIemReqList) {
    List<VoteIemEntity> resEntityList = new ArrayList<>();
    for(VoteIemRequest voteIem : voteIemReqList) {
      VoteIemEntity resVoteIem = voteIemRepository.save(VoteIemEntity.builder()
          .voteSn(voteIem.getVoteSsno())
          .qestnSn(voteIem.getQuestionSsno())
          .iemSn(voteIem.getItemSsno())
          .iemNm(voteIem.getItemName())
          .build());

      resEntityList.add(resVoteIem);
    }

    return resEntityList.stream().map(entity -> VoteIemResponse.builder()
        .voteSsno(entity.getVoteSn())
        .questionSsno(entity.getQestnSn())
        .itemSsno(entity.getIemSn())
        .itemName(entity.getIemNm())
        .build())
        .toList();
  }

  @Transactional
  public Long removeVoteIem(VoteIemRequest voteIemRequest) {
    if (!ObjectUtils.isEmpty(voteIemRequest.getVoteSsno())) {
      return voteRepository.deleteByVoteSnForVoteIem(voteIemRequest.getVoteSsno());
    }else {
      return 0L;
    }
  }
}
