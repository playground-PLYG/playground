package com.playground.api.vote.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.QestnAnswerEntity;
import com.playground.api.vote.entity.QestnAnswerPK;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.model.QestnAnswerRequest;
import com.playground.api.vote.model.QestnAnswerResponse;
import com.playground.api.vote.model.QestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResponse;
import com.playground.api.vote.repository.QestnAnswerRepository;
import com.playground.api.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {
  private final QestnAnswerRepository qestnAnswerRepository;
  private final VoteRepository voteRepository;

  @Transactional(readOnly = true)
  public Boolean isDuplicateVote(QestnAnswerRequest reqData) {
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
      VoteResponse voteResponse = VoteResponse.builder().voteSsno(voteEntity.getVoteSn()).voteKindCode(voteEntity.getVoteKndCode())
          .voteSubject(voteEntity.getVoteSj()).anonymityVoteAlternative(voteEntity.getAnnymtyVoteAt()).voteBeginDate(voteEntity.getVoteBeginDt())
          .voteEndDate(voteEntity.getVoteEndDt()).voteDeleteAlternative(voteEntity.getVoteDeleteAt()).build();

      List<QestnResponse> qestnResponseList = voteRepository.getQestnDetail(reqData.getVoteSsno(), reqData.getQuestionSsno());
      if (qestnResponseList.size() != 0) {
        voteResponse.setQestnResponseList(qestnResponseList);
      }
      return voteResponse;
    } else {
      return new VoteResponse();
    }

  }

  @Transactional(readOnly = true)
  public List<QestnAnswerResponse> getAnswer(QestnAnswerRequest reqData) {
    log.debug("AnswerService.getAnswer ::: request ::: {}", reqData);
    List<QestnAnswerEntity> qestnAnswerEntities =
        qestnAnswerRepository.findBySsno(QestnAnswerEntity.builder().voteSn(reqData.getVoteSsno()).qestnSn(reqData.getQuestionSsno()).build());

    return qestnAnswerEntities.stream()
        .map(entity -> QestnAnswerResponse.builder().answerSsno(entity.getAnswerSn()).voteSsno(entity.getVoteSn()).questionSsno(entity.getQestnSn())
            .itemSsno(entity.getIemSn()).answerUserId(entity.getAnswerUserId()).answerContents(entity.getAnswerCn()).build())
        .toList();
  }


  @Transactional
  public List<QestnAnswerResponse> addAnswer(List<QestnAnswerRequest> reqDataList) {
    List<QestnAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      resEntityList.add(QestnAnswerEntity.builder().voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno()).iemSn(req.getItemSsno())
          .answerUserId(StringUtils.defaultString(req.getAnswerUserId())).answerCn(StringUtils.defaultString(req.getAnswerContents())).build());
    });

    List<QestnAnswerEntity> saveAllEntities = qestnAnswerRepository.saveAll(resEntityList);
    return saveAllEntities.stream()
        .map(entity -> QestnAnswerResponse.builder().answerSsno(entity.getAnswerSn()).voteSsno(entity.getVoteSn()).questionSsno(entity.getQestnSn())
            .itemSsno(entity.getIemSn()).answerUserId(entity.getAnswerUserId()).answerContents(entity.getAnswerCn()).build())
        .toList();
  }

  @Transactional
  public List<QestnAnswerResponse> modifyAnswer(List<QestnAnswerRequest> reqDataList) {
    List<QestnAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      QestnAnswerEntity reqAnswer = QestnAnswerEntity.builder().answerSn(req.getAnswerSsno()).voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno())
          .answerUserId(StringUtils.defaultString(req.getAnswerUserId())).iemSn(req.getItemSsno())
          .answerCn(StringUtils.defaultString(req.getAnswerContents())).build();

      QestnAnswerEntity resAnswer = qestnAnswerRepository.selectByEntity(reqAnswer);

      if (!ObjectUtils.isEmpty(resAnswer)) {
        qestnAnswerRepository.deleteById(QestnAnswerPK.builder().answerSn(resAnswer.getAnswerSn()).voteSn(resAnswer.getVoteSn())
            .qestnSn(resAnswer.getQestnSn()).iemSn(resAnswer.getIemSn()).build());
      }
      QestnAnswerEntity saveAnswer = qestnAnswerRepository.save(reqAnswer);
      resEntityList.add(saveAnswer);
    });

    return resEntityList.stream()
        .map(entity -> QestnAnswerResponse.builder().answerSsno(entity.getAnswerSn()).voteSsno(entity.getVoteSn()).questionSsno(entity.getQestnSn())
            .itemSsno(entity.getIemSn()).answerUserId(entity.getAnswerUserId()).answerContents(entity.getAnswerCn()).build())
        .toList();
  }

  @Transactional
  public Long removeAnswer(QestnAnswerRequest qestnAnswerRequest) {
    if (!ObjectUtils.isEmpty(qestnAnswerRequest.getAnswerSsno())) {
      return qestnAnswerRepository.deleteBySsno(qestnAnswerRequest.getAnswerSsno());
    } else {
      return 0L;
    }
  }
}
