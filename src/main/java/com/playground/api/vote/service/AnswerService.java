package com.playground.api.vote.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.QestnAnswerEntity;
import com.playground.api.vote.entity.QestnAnswerPK;
import com.playground.api.vote.model.QestnAnswerRequest;
import com.playground.api.vote.model.QestnAnswerResponse;
import com.playground.api.vote.repository.QestnAnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerService {
  private final QestnAnswerRepository qestnAnswerRepository;

  @Transactional(readOnly = true)
  public List<QestnAnswerResponse> getAnswer(QestnAnswerRequest reqData) {
    log.debug("AnswerService.getAnswer ::: request ::: {}", reqData);
    List<QestnAnswerEntity> qestnAnswerEntities =
        qestnAnswerRepository.findBySsno(QestnAnswerEntity.builder()
            .voteSn(reqData.getVoteSsno()).qestnSn(reqData.getQuestionSsno()).
            build());

    return qestnAnswerEntities.stream()
        .map(entity -> QestnAnswerResponse.builder()
            .answerSsno(entity.getAnswerSn()).voteSsno(entity.getVoteSn())
            .questionSsno(entity.getQestnSn()).itemId(entity.getIemId())
            .answerUserId(entity.getAnswerUserId()).answerContents(entity.getAnswerCn())
            .build())
        .toList();
  }


  @Transactional
  public List<QestnAnswerResponse> addAnswer(List<QestnAnswerRequest> reqDataList) {
    List<QestnAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      resEntityList.add(QestnAnswerEntity.builder()
          .voteSn(req.getVoteSsno()).qestnSn(req.getQuestionSsno())
          .iemId(req.getItemId()).answerUserId(stringNvl(req.getAnswerUserId()))
          .answerCn(stringNvl(req.getAnswerContents()))
          .build());
    });

    List<QestnAnswerEntity> saveAllEntities = qestnAnswerRepository.saveAll(resEntityList);
    return saveAllEntities.stream()
        .map(entity -> QestnAnswerResponse.builder()
            .answerSsno(entity.getAnswerSn()).voteSsno(entity.getVoteSn())
            .questionSsno(entity.getQestnSn()).itemId(entity.getIemId())
            .answerUserId(entity.getAnswerUserId()).answerContents(entity.getAnswerCn())
            .build())
        .toList();
  }
  
  @Transactional
  public List<QestnAnswerResponse> modifyAnswer(List<QestnAnswerRequest> reqDataList) {
    List<QestnAnswerEntity> resEntityList = new ArrayList<>();
    reqDataList.forEach(req -> {
      QestnAnswerEntity reqAnswer = QestnAnswerEntity.builder()
          .answerSn(req.getAnswerSsno()).voteSn(req.getVoteSsno())
          .qestnSn(req.getQuestionSsno()).answerUserId(stringNvl(req.getAnswerUserId()))
          .iemId(req.getItemId()).answerCn(stringNvl(req.getAnswerContents()))
          .build();
      
       QestnAnswerEntity resAnswer = qestnAnswerRepository.selectByEntity(reqAnswer);
       
      if (!ObjectUtils.isEmpty(resAnswer)) {
        qestnAnswerRepository.deleteById(QestnAnswerPK.builder()
            .answerSn(resAnswer.getAnswerSn()).voteSn(resAnswer.getVoteSn())
            .qestnSn(resAnswer.getQestnSn()).iemId(resAnswer.getIemId())
            .build());
      }
      QestnAnswerEntity saveAnswer = qestnAnswerRepository.save(reqAnswer);
      resEntityList.add(saveAnswer);
    });

    return resEntityList.stream().map(entity -> QestnAnswerResponse.builder()
            .answerSsno(entity.getAnswerSn()).voteSsno(entity.getVoteSn())
            .questionSsno(entity.getQestnSn()).itemId(entity.getIemId())
            .answerUserId(entity.getAnswerUserId()).answerContents(entity.getAnswerCn())
            .build())
        .toList();
  }
  
  @Transactional
  public Long removeAnswer(QestnAnswerRequest qestnAnswerRequest) {
    if(!ObjectUtils.isEmpty(qestnAnswerRequest.getAnswerSsno())) {
      return qestnAnswerRepository.deleteBySsno(qestnAnswerRequest.getAnswerSsno());
    }else {
      return 0L;
    }
  }
  
  private String stringNvl(String str) {
    String reStr ="";
    if(!ObjectUtils.isEmpty(str)) {
      return str;
    }else {
      return reStr;
    }
  }
  
}
