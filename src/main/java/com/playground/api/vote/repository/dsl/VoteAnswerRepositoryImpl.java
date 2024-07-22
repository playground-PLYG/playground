package com.playground.api.vote.repository.dsl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.QVoteAnswerEntity;
import com.playground.api.vote.entity.QVoteQestnEntity;
import com.playground.api.vote.entity.QVoteQestnIemEntity;
import com.playground.api.vote.entity.VoteAnswerEntity;
import com.playground.api.vote.model.VoteAnswerSubResponse;
import com.playground.api.vote.model.VoteRequest;
import com.playground.api.vote.model.VoteResultDetailDetailResponse;
import com.playground.api.vote.model.VoteResultDetailResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class VoteAnswerRepositoryImpl implements VoteAnswerRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QVoteAnswerEntity tbVoteAnswer = QVoteAnswerEntity.voteAnswerEntity;
  QVoteQestnIemEntity tbQVoteQestnIem = QVoteQestnIemEntity.voteQestnIemEntity;
  QVoteQestnEntity tbVoteQestn = QVoteQestnEntity.voteQestnEntity;

  @Override
  public List<VoteAnswerEntity> getVoteAnswerEntityList(VoteAnswerEntity reqData) {
    return queryFactory.selectFrom(tbVoteAnswer)
        .where(tbVoteAnswer.voteSn.eq(reqData.getVoteSn())
            .and(tbVoteAnswer.qestnSn.eq(reqData.getQestnSn()))
            .and(tbVoteAnswer.answerUserId.eq(reqData.getAnswerUserId())))
        .fetch();
  }

  @Override
  public List<VoteAnswerSubResponse> getMyVoteAnswerList(VoteRequest reqData) {
    return queryFactory.selectFrom(tbVoteAnswer)
        .where(tbVoteAnswer.voteSn.eq(reqData.getVoteSsno())
            .and(tbVoteAnswer.answerUserId.eq(reqData.getAnswerUserId())))
        .orderBy(tbVoteAnswer.qestnSn.asc())
        .transform(
            groupBy(tbVoteAnswer.qestnSn)
                .list(Projections.fields(VoteAnswerSubResponse.class, 
                    tbVoteAnswer.qestnSn.as("questionSsno"),
                    list(tbVoteAnswer.iemSn).as("voteAnswerSubList")
                        )
                    )
                );
  }

  @Override
  public List<VoteResultDetailResponse> getVoteQestnResult(VoteRequest reqData) {
    return queryFactory.select(tbVoteAnswer).from(tbVoteAnswer)
        .leftJoin(tbQVoteQestnIem)
        .on(tbVoteAnswer.voteSn.eq(tbQVoteQestnIem.voteSn)
            .and(tbVoteAnswer.qestnSn.eq(tbQVoteQestnIem.qestnSn))
            .and(tbVoteAnswer.iemSn.eq(tbQVoteQestnIem.iemSn)))
        .leftJoin(tbVoteQestn)
        .on(tbVoteAnswer.voteSn.eq(tbVoteQestn.voteSn)
            .and(tbVoteAnswer.qestnSn.eq(tbVoteQestn.qestnSn)))
        .where(tbVoteAnswer.voteSn.eq(reqData.getVoteSsno()))
        .groupBy(tbVoteAnswer.qestnSn, tbVoteAnswer.iemSn, 
            tbQVoteQestnIem.iemNm, tbVoteQestn.qestnCn, 
            tbVoteQestn.compnoChoiseAt, tbVoteQestn.annymtyVoteAt, tbQVoteQestnIem.iemIdntfcId)
        .orderBy(tbVoteAnswer.qestnSn.asc(), tbVoteAnswer.iemSn.asc())
        .transform(
            groupBy(tbVoteAnswer.qestnSn)
            .list(
                Projections.fields(VoteResultDetailResponse.class, 
                    tbVoteAnswer.qestnSn.as("questionSsno"), 
                    tbVoteQestn.qestnCn.as("questionContents"),
                tbVoteQestn.compnoChoiseAt.as("compoundNumberChoiceAlternative"), 
                tbVoteQestn.annymtyVoteAt.as("anonymityVoteAlternative"),
                list(
                    Projections.fields(VoteResultDetailDetailResponse.class, 
                        tbVoteAnswer.iemSn.as("itemSsno"), 
                        tbQVoteQestnIem.iemNm.as("itemName"),
                        Wildcard.count.as("itemCount"), 
                        tbQVoteQestnIem.iemIdntfcId.as("itemIdentificationId"))
                    ).as("resultDetailList")
                )
                )
            );
  }

  @Override
  public List<String> getAnswerUserIds(Integer voteSsno, Integer questionSsno, Integer itemSsno) {
    return queryFactory.select(tbVoteAnswer.answerUserId).from(tbVoteAnswer)
        .where(tbVoteAnswer.voteSn.eq(voteSsno).and(tbVoteAnswer.qestnSn.eq(questionSsno)).and(tbVoteAnswer.iemSn.eq(itemSsno))).fetch();
  }

}
