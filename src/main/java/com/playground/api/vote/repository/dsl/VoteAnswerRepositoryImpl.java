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
import com.playground.api.vote.entity.QVoteAnswerEntity;
import com.playground.api.vote.entity.QVoteQestnEntity;
import com.playground.api.vote.entity.QVoteQestnIemEntity;
import com.playground.api.vote.entity.VoteAnswerEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class VoteAnswerRepositoryImpl implements VoteAnswerRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QVoteAnswerEntity tbQestnAnswer = QVoteAnswerEntity.voteAnswerEntity;
  QVoteQestnIemEntity tbQVoteIem = QVoteQestnIemEntity.voteQestnIemEntity;
  QVoteQestnEntity tbQestn = QVoteQestnEntity.voteQestnEntity;

  @Override
  public List<VoteAnswerEntity> findBySsno(VoteAnswerEntity reqData) {
    return queryFactory.selectFrom(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSn()).and(checkQestnSSno(reqData.getQestnSn())).and(checkUserID(reqData.getAnswerUsrId())))
        .orderBy(tbQestnAnswer.voteSn.asc(), tbQestnAnswer.qestnSn.asc()).fetch();
  }

  @Override
  public Long deleteBySsno(Integer answerSsno) {
    return queryFactory.delete(tbQestnAnswer)// .where(tbQestnAnswer.answerSn.eq(answerSsno))
        .execute();
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
    return tbQestnAnswer.answerUsrId.eq(answerUserId);
  }

  @Override
  public VoteAnswerEntity selectByEntity(VoteAnswerEntity reqData) {
    return queryFactory.selectFrom(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSn()).and(tbQestnAnswer.qestnSn.eq(reqData.getQestnSn()))
            .and(checkAnonymous(
                queryFactory.select(tbQestnAnswer.answerUsrId).from(tbQestnAnswer)
                    .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSn()).and(tbQestnAnswer.qestnSn.eq(reqData.getQestnSn()))).fetchOne(),
                reqData.getAnswerUsrId())))
        .fetchOne();
  }

  private BooleanExpression checkAnonymous(String tbAnswerUserId, String reqAnswerUserId) {
    if (ObjectUtils.isEmpty(reqAnswerUserId)) {
      return null;
    } else if ("anonymous".equals(tbAnswerUserId)) {
      return tbQestnAnswer.registUsrId.eq(reqAnswerUserId);
    } else {
      return tbQestnAnswer.answerUsrId.eq(reqAnswerUserId);
    }
  }

  @Override
  public Long selectByAnswerUserId(Integer voteSsno, String answerUserId) {
    return queryFactory.select(Wildcard.count).from(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(voteSsno).and(tbQestnAnswer.answerUsrId.eq(answerUserId))).fetchOne();
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

    Long totalVoterCount = queryFactory.select(tbQestnAnswer.answerUsrId.countDistinct()).from(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(reqData.getVoteSsno())).fetchOne();

    Long questionCount = queryFactory.select(Wildcard.count).from(tbQestn).where(tbQestn.voteSn.eq(reqData.getVoteSsno())).fetchOne();
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
    return queryFactory.select(tbQestnAnswer.answerUsrId).from(tbQestnAnswer)
        .where(tbQestnAnswer.voteSn.eq(voteSsno).and(tbQestnAnswer.qestnSn.eq(questionSsno)).and(tbQestnAnswer.iemSn.eq(itemSsno))).fetch();
  }


}
