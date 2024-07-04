package com.playground.api.vote.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.VoteAnswerEntity;
import com.playground.api.vote.entity.VoteAnswerPK;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.model.VoteAnswerRequest;
import com.playground.api.vote.model.VoteAnswerResponse;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.repository.VoteAnswerRepository;
import com.playground.api.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {
  private final VoteAnswerRepository qestnAnswerRepository;
  private final VoteRepository voteRepository;

  @Transactional(readOnly = true)
  public Boolean isDuplicateVote(VoteAnswerRequest reqData) {
    Boolean isDuplicate = true; // true = 중복투표, false = 처음투표
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno()) && !StringUtils.isEmpty(reqData.getAnswerUserId())) {
      Long resultCount = qestnAnswerRepository.selectByAnswerUserId(reqData.getVoteSsno(), reqData.getAnswerUserId());
      if (resultCount.intValue() > 0) {
        isDuplicate = true; // 중복
      } else {
        isDuplicate = false; // 처음
      }
    }
    return isDuplicate;
  }

  @Transactional(readOnly = true)
  public VoteResponse getVoteDetail(VoteRequest reqData) {
    if (!ObjectUtils.isEmpty(reqData.getVoteSsno())) {
      VoteEntity voteEntity = voteRepository.findById(reqData.getVoteSsno()).orElse(VoteEntity.builder().build());
      VoteResponse voteResponse = VoteResponse.builder().voteSsno(voteEntity.getVoteSn())// .voteKindCode(voteEntity.getVoteKndCode())
          .voteSubject(voteEntity.getVoteSj())// .anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt())
          .voteBeginDate(voteEntity.getVoteBeginDt()).voteEndDate(voteEntity.getVoteEndDt())// .voteDeleteAlternative(voteEntity.getVoteDeleteAt())
          .build();

      List<VoteQestnResponse> qestnResponseList = voteRepository.getQestnDetail(reqData.getVoteSsno(), reqData.getQuestionSsno());
      if (qestnResponseList.size() != 0) {
        voteResponse.setQestnResponseList(qestnResponseList);
      }
      return voteResponse;
    } else {
      return new VoteResponse();
    }

  }

  @Transactional(readOnly = true)
  public List<VoteAnswerResponse> getAnswer(VoteAnswerRequest reqData) {
    log.debug("AnswerService.getAnswer ::: request ::: {}", reqData);

    // 임시 조치
    return new ArrayList<VoteAnswerResponse>();
  }


  @Transactional
  public List<VoteAnswerResponse> addAnswer(List<VoteAnswerRequest> reqDataList) {
    List<VoteAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      resEntityList.add(VoteAnswerEntity.builder().voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno()).iemSn(req.getItemSsno())
          // .answerUserId(StringUtils.defaultString(req.getAnswerUserId())).answerCn(StringUtils.defaultString(req.getAnswerContents()))
          .build());
    });

    // 임시 조치
    return new ArrayList<VoteAnswerResponse>();
  }

  @Transactional
  public List<VoteAnswerResponse> modifyAnswer(List<VoteAnswerRequest> reqDataList) {
    List<VoteAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      VoteAnswerEntity reqAnswer = VoteAnswerEntity.builder()// .answerSn(req.getAnswerSsno())
          .voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno())
          // .answerUsrId(StringUtils.defaultString(req.getAnswerUserId()))
          .iemSn(req.getItemSsno())
          // .answerCn(StringUtils.defaultString(req.getAnswerContents()))
          .build();

      VoteAnswerEntity resAnswer = qestnAnswerRepository.selectByEntity(reqAnswer);

      if (!ObjectUtils.isEmpty(resAnswer)) {
        qestnAnswerRepository.deleteById(VoteAnswerPK.builder()// .answerSn(resAnswer.getAnswerSn())
            .voteSn(resAnswer.getVoteSn()).qestnSn(resAnswer.getQestnSn()).iemSn(resAnswer.getIemSn()).build());
      }
      VoteAnswerEntity saveAnswer = qestnAnswerRepository.save(reqAnswer);
      resEntityList.add(saveAnswer);
    });

    // 임시 조치
    return new ArrayList<VoteAnswerResponse>();
  }

  @Transactional
  public Long removeAnswer(VoteAnswerRequest qestnAnswerRequest) {
    if (!ObjectUtils.isEmpty(qestnAnswerRequest.getAnswerSsno())) {
      return qestnAnswerRepository.deleteBySsno(qestnAnswerRequest.getAnswerSsno());
    } else {
      return 0L;
    }
  }
}
