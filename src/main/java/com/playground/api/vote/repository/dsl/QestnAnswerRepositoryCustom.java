package com.playground.api.vote.repository.dsl;

import java.util.List;
import com.playground.api.vote.entity.QestnAnswerEntity;

public interface QestnAnswerRepositoryCustom {
  List<QestnAnswerEntity> findBySsno(QestnAnswerEntity qestnAnswerEntity);
  
  Long deleteBySsno(Integer answerSsno);
  
  QestnAnswerEntity selectByEntity(QestnAnswerEntity qestnAnswerEntity);
}
