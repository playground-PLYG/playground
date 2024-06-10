package com.playground.api.vote.repository.dsl;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.QQestnAnswerEntity;
import com.playground.api.vote.entity.QestnAnswerEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QestnAnswerRepositoryImpl implements QestnAnswerRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QQestnAnswerEntity tbQestnAnswer = QQestnAnswerEntity.qestnAnswerEntity;

  @Override
  public List<QestnAnswerEntity> findBySsno(QestnAnswerEntity reqData) {
    return queryFactory.selectFrom(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSn())
            .and(checkQestnSSno(reqData.getQestnSn()))
            .and(checkUserID(reqData.getAnswerUserId())))
        .orderBy(tbQestnAnswer.voteSn.asc(), tbQestnAnswer.qestnSn.asc())
        .fetch();
  }

  @Override
  public Long deleteBySsno(Integer answerSsno) {
    return queryFactory.delete(tbQestnAnswer).where(tbQestnAnswer.answerSn.eq(answerSsno)).execute();
  }
  
  private BooleanExpression checkQestnSSno(Integer qestionSsno) {
    if (ObjectUtils.isEmpty(qestionSsno)) {
      return null;
    }
    return tbQestnAnswer.qestnSn.eq(qestionSsno);
  }
  
  private BooleanExpression checkUserID(String answerUserId) {
    if (ObjectUtils.isEmpty(answerUserId)) {
      return null;
    }
    return tbQestnAnswer.answerUserId.eq(answerUserId);
  }

  @Override
  public QestnAnswerEntity selectByEntity(QestnAnswerEntity reqData) {
    return queryFactory.selectFrom(tbQestnAnswer)
        .where(tbQestnAnswer.answerSn.eq(reqData.getAnswerSn())
            .and(tbQestnAnswer.voteSn.eq(reqData.getVoteSn()))
            .and(tbQestnAnswer.qestnSn.eq(reqData.getQestnSn()))
            .and(checkAnonymous(
                queryFactory.select(tbQestnAnswer.answerUserId).from(tbQestnAnswer)
                .where(tbQestnAnswer.answerSn.eq(reqData.getAnswerSn())
                    .and(tbQestnAnswer.voteSn.eq(reqData.getVoteSn()))
                    .and(tbQestnAnswer.qestnSn.eq(reqData.getQestnSn())))
                .fetchOne()
                , reqData.getAnswerUserId()))
            )
        .fetchOne();
  }
  
  private BooleanExpression checkAnonymous(String tbAnswerUserId, String reqAnswerUserId) {
    if (ObjectUtils.isEmpty(reqAnswerUserId)) {
      return null;
    }else if("anonymous".equals(tbAnswerUserId)) {
      return tbQestnAnswer.registUsrId.eq(reqAnswerUserId);
    }else {
      return tbQestnAnswer.answerUserId.eq(reqAnswerUserId);
    }
  }

  @Override
  public Long selectByAnswerUserId(Integer voteSsno, String answerUserId) {
    return queryFactory
        .select(Wildcard.count)
        .from(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(voteSsno)
            .and(tbQestnAnswer.answerUserId.eq(answerUserId)))
        .fetchOne();
  }
}
