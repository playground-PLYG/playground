package com.playground.api.vote.repository.dsl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.playground.api.statistics.model.StatisticsDetailDetailResponse;
import com.playground.api.statistics.model.StatisticsDetailResponse;
import com.playground.api.statistics.model.StatisticsRequest;
import com.playground.api.statistics.model.StatisticsResponse;
import com.playground.api.vote.entity.QQestnAnswerEntity;
import com.playground.api.vote.entity.QQestnEntity;
import com.playground.api.vote.entity.QVoteIemEntity;
import com.playground.api.vote.entity.QestnAnswerEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class QestnAnswerRepositoryImpl implements QestnAnswerRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QQestnAnswerEntity tbQestnAnswer = QQestnAnswerEntity.qestnAnswerEntity;
  QVoteIemEntity tbQVoteIem = QVoteIemEntity.voteIemEntity;
  QQestnEntity tbQestn = QQestnEntity.qestnEntity;

  @Override
  public List<QestnAnswerEntity> findBySsno(QestnAnswerEntity reqData) {
    return queryFactory.selectFrom(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSn()).and(checkQestnSSno(reqData.getQestnSn())).and(checkUserID(reqData.getAnswerUserId())))
        .orderBy(tbQestnAnswer.voteSn.asc(), tbQestnAnswer.qestnSn.asc()).fetch();
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
        .where(tbQestnAnswer.answerSn.eq(reqData.getAnswerSn()).and(tbQestnAnswer.voteSn.eq(reqData.getVoteSn()))
            .and(tbQestnAnswer.qestnSn.eq(reqData.getQestnSn()))
            .and(checkAnonymous(
                queryFactory.select(tbQestnAnswer.answerUserId).from(tbQestnAnswer).where(tbQestnAnswer.answerSn.eq(reqData.getAnswerSn())
                    .and(tbQestnAnswer.voteSn.eq(reqData.getVoteSn())).and(tbQestnAnswer.qestnSn.eq(reqData.getQestnSn()))).fetchOne(),
                reqData.getAnswerUserId())))
        .fetchOne();
  }

  private BooleanExpression checkAnonymous(String tbAnswerUserId, String reqAnswerUserId) {
    if (ObjectUtils.isEmpty(reqAnswerUserId)) {
      return null;
    } else if ("anonymous".equals(tbAnswerUserId)) {
      return tbQestnAnswer.registUsrId.eq(reqAnswerUserId);
    } else {
      return tbQestnAnswer.answerUserId.eq(reqAnswerUserId);
    }
  }

  @Override
  public Long selectByAnswerUserId(Integer voteSsno, String answerUserId) {
    return queryFactory.select(Wildcard.count).from(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(voteSsno).and(tbQestnAnswer.answerUserId.eq(answerUserId))).fetchOne();
  }

  @Override
  public List<StatisticsDetailResponse> selectVoteDetailStatistics(StatisticsRequest reqData) {
    return queryFactory.select(tbQestnAnswer).from(tbQestnAnswer).leftJoin(tbQVoteIem)
        .on(tbQestnAnswer.voteSn.eq(tbQVoteIem.voteSn).and(tbQestnAnswer.qestnSn.eq(tbQVoteIem.qestnSn))
            .and(tbQestnAnswer.iemSn.eq(tbQVoteIem.iemSn)))
        .leftJoin(tbQestn).on(tbQestnAnswer.voteSn.eq(tbQestn.voteSn).and(tbQestnAnswer.qestnSn.eq(tbQestn.qestnSn)))
        .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSsno()).and(checkQestnSSnoAtStatistic(reqData.getQuestionSsno()))
            .and(checkItemSSnoAtStatistic(reqData.getItemSsno())))
        .groupBy(tbQestnAnswer.qestnSn, tbQestnAnswer.iemSn, tbQVoteIem.iemNm, tbQestn.qestnCn)
        .orderBy(tbQestnAnswer.qestnSn.asc(), tbQestnAnswer.iemSn.asc())
        // .transform(groupBy(tbQestnAnswer.qestnSn, tbQestnAnswer.iemSn).list(
        .transform(groupBy(tbQestnAnswer.qestnSn).list(Projections.fields(StatisticsDetailResponse.class, tbQestnAnswer.qestnSn.as("questionSsno"),
            tbQestn.qestnCn.as("questionContents"), list(Projections.fields(StatisticsDetailDetailResponse.class, tbQestnAnswer.iemSn.as("itemSsno"),
                Wildcard.count.as("itemCount"), tbQVoteIem.iemNm.as("itemName"))).as("staDetailDetailList"))));
  }

  @Override
  public StatisticsResponse selectVoteStatistics(StatisticsRequest reqData) {
    Long totalVoteCount = queryFactory.select(Wildcard.count).from(tbQestnAnswer).where(tbQestnAnswer.voteSn.eq(reqData.getVoteSsno())).fetchOne();

    Long totalVoterCount = queryFactory.select(tbQestnAnswer.answerUserId.countDistinct()).from(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSsno())).fetchOne();

    Long questionCount = queryFactory.select(Wildcard.count).from(tbQestn).where(tbQestn.voteSn.eq(reqData.getVoteSsno())).fetchOne();

    // List<StatisticsDetailResponse> detailResponse = queryFactory.select(
    // Projections.fields(StatisticsDetailResponse.class,
    // tbQestnAnswer.qestnSn.as("questionSsno"),
    // tbQestnAnswer.iemSn.as("itemSsno"),
    // Wildcard.count.as("itemCount"),
    // tbQVoteIem.iemNm.as("itemName"),
    // tbQestn.qestnCn.as("questionContents")))
    // .from(tbQestnAnswer)
    // .join(tbQVoteIem)
    // .on(tbQestnAnswer.voteSn.eq(tbQVoteIem.voteSn)
    // .and(tbQestnAnswer.qestnSn.eq(tbQVoteIem.qestnSn))
    // .and(tbQestnAnswer.iemSn.eq(tbQVoteIem.iemSn)))
    // .join(tbQestn)
    // .on(tbQestnAnswer.voteSn.eq(tbQestn.voteSn)
    // .and(tbQestnAnswer.qestnSn.eq(tbQestn.qestnSn)))
    // .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSsno())
    // .and(checkQestnSSnoAtStatistic(reqData.getQuestionSsno()))
    // .and(checkItemSSnoAtStatistic(reqData.getItemSsno())))
    // .groupBy(tbQestnAnswer.qestnSn, tbQestnAnswer.iemSn, tbQVoteIem.iemNm, tbQestn.qestnCn)
    // .orderBy(tbQestnAnswer.qestnSn.asc(), tbQestnAnswer.iemSn.asc())
    // .fetch();

    return StatisticsResponse.builder().totalVoteCount(totalVoteCount.intValue()).totalVoterCount(totalVoterCount.intValue())
        .questionCount(questionCount.intValue())
        // .staDetailList(detailResponse)
        .voteSsno(reqData.getVoteSsno()).build();
  }

  private BooleanExpression checkQestnSSnoAtStatistic(Integer qestionSsno) {
    if (ObjectUtils.isEmpty(qestionSsno) || qestionSsno == 0) {
      return null;
    }
    return tbQestnAnswer.qestnSn.eq(qestionSsno);
  }

  // checkQestnSSno
  private BooleanExpression checkItemSSnoAtStatistic(Integer itemSsno) {
    if (ObjectUtils.isEmpty(itemSsno) || itemSsno == 0) {
      return null;
    }
    return tbQestnAnswer.qestnSn.eq(itemSsno);
  }

  @Override
  public List<String> selectAnswerUserIds(Integer voteSsno, Integer questionSsno, Integer itemSsno) {
    return queryFactory.select(tbQestnAnswer.answerUserId).from(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(voteSsno).and(tbQestnAnswer.qestnSn.eq(questionSsno)).and(tbQestnAnswer.iemSn.eq(itemSsno))).fetch();
  }


}
