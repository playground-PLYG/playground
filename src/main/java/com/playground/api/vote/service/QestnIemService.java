package com.playground.api.vote.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.VoteQestnEntity;
import com.playground.api.vote.entity.VoteQestnIemEntity;
import com.playground.api.vote.model.VoteQestnIemRequest;
import com.playground.api.vote.model.VoteQestnIemResponse;
import com.playground.api.vote.model.VoteQestnRequest;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.repository.VoteQestnIemRepository;
import com.playground.api.vote.repository.VoteQestnRepository;
import com.playground.api.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QestnIemService {
  private final VoteRepository voteRepository;
  private final VoteQestnRepository qestnRepository;
  private final VoteQestnIemRepository voteIemRepository;
  private final ModelMapper modelMapper;

  @Transactional
  public List<VoteQestnResponse> addQestn(List<VoteQestnRequest> qestnReqList) {
    List<VoteQestnEntity> resEntityList = new ArrayList<>();
    for (VoteQestnRequest qestn : qestnReqList) {
      resEntityList.add(VoteQestnEntity.builder().voteSn(qestn.getVoteSsno()).qestnCn(qestn.getQuestionContents())
          .compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());
    }

    List<VoteQestnEntity> saveAllEntities = qestnRepository.saveAll(resEntityList);
    return saveAllEntities.stream().map(entity -> VoteQestnResponse.builder().questionSsno(entity.getQestnSn()).voteSsno(entity.getVoteSn())
        .questionContents(entity.getQestnCn()).compoundNumberChoiceAlternative(entity.getCompnoChoiseAt()).build()).toList();
  }

  @Transactional
  public List<VoteQestnResponse> modifyQestn(List<VoteQestnRequest> qestnReqList) {
    List<VoteQestnEntity> resEntityList = new ArrayList<>();
    for (VoteQestnRequest qestn : qestnReqList) {
      VoteQestnEntity resQestn = qestnRepository.save(VoteQestnEntity.builder().qestnSn(qestn.getQuestionSsno()).voteSn(qestn.getVoteSsno())
          .qestnCn(qestn.getQuestionContents()).compnoChoiseAt(qestn.getCompoundNumberChoiceAlternative()).build());

      resEntityList.add(resQestn);
    }

    return resEntityList.stream().map(entity -> VoteQestnResponse.builder().questionSsno(entity.getQestnSn()).voteSsno(entity.getVoteSn())
        .questionContents(entity.getQestnCn()).compoundNumberChoiceAlternative(entity.getCompnoChoiseAt()).build()).toList();
  }

  @Transactional
  public Long removeQestn(VoteQestnRequest qestnRequest) {
    if (!ObjectUtils.isEmpty(qestnRequest.getVoteSsno())) {
      return voteRepository.deleteByVoteSnForQestn(qestnRequest.getVoteSsno());
    } else {
      return 0L;
    }
  }

  @Transactional
  public List<VoteQestnIemResponse> addVoteIem(List<VoteQestnIemRequest> voteIemReqList) {
    List<VoteQestnIemEntity> resEntityList = new ArrayList<>();
    for (VoteQestnIemRequest voteIem : voteIemReqList) {
      resEntityList.add(VoteQestnIemEntity.builder().voteSn(voteIem.getVoteSsno()).qestnSn(voteIem.getQuestionSsno()).iemSn(voteIem.getItemSsno())
          .iemNm(voteIem.getItemName()).build());
    }

    List<VoteQestnIemEntity> saveAllEntities = voteIemRepository.saveAll(resEntityList);
    return saveAllEntities.stream().map(entity -> VoteQestnIemResponse.builder().voteSsno(entity.getVoteSn()).questionSsno(entity.getQestnSn())
        .itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList();
  }

  @Transactional
  public List<VoteQestnIemResponse> modifyVoteIem(List<VoteQestnIemRequest> voteIemReqList) {
    List<VoteQestnIemEntity> resEntityList = new ArrayList<>();
    for (VoteQestnIemRequest voteIem : voteIemReqList) {
      VoteQestnIemEntity resVoteIem = voteIemRepository.save(VoteQestnIemEntity.builder().voteSn(voteIem.getVoteSsno())
          .qestnSn(voteIem.getQuestionSsno()).iemSn(voteIem.getItemSsno()).iemNm(voteIem.getItemName()).build());

      resEntityList.add(resVoteIem);
    }

    return resEntityList.stream().map(entity -> VoteQestnIemResponse.builder().voteSsno(entity.getVoteSn()).questionSsno(entity.getQestnSn())
        .itemSsno(entity.getIemSn()).itemName(entity.getIemNm()).build()).toList();
  }

  @Transactional
  public Long removeVoteIem(VoteQestnIemRequest voteIemRequest) {
    if (!ObjectUtils.isEmpty(voteIemRequest.getVoteSsno())) {
      return voteRepository.deleteByVoteSnForVoteIem(voteIemRequest.getVoteSsno());
    } else {
      return 0L;
    }
  }
}
