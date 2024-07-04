package com.playground.api.vote.repository.dsl;

import static com.playground.api.restaurant.entity.QRstrntEntity.rstrntEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.vote.entity.QVoteEntity;
import com.playground.api.vote.entity.QVoteQestnEntity;
import com.playground.api.vote.entity.QVoteQestnIemEntity;
import com.playground.api.vote.model.VoteRstrntIemResponse;
import com.playground.api.vote.model.VoteRstrntResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class VoteRstrntRepositoryImpl implements VoteRstrntRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QVoteEntity tbVote = QVoteEntity.voteEntity;
  QVoteQestnEntity tbQestn = QVoteQestnEntity.voteQestnEntity;
  QVoteQestnIemEntity tbIem = QVoteQestnIemEntity.voteQestnIemEntity;

  @Override
  public Integer getVoteRstrntCount() {
    StringTemplate voteRegist = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", tbVote.registDt, "YYYYMMDD");

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String today = date.format(formatter);

    return Math.toIntExact(queryFactory.select(tbVote.count()).from(tbVote).where(// tbVote.voteKndCode.eq("LUN").and(tbVote.voteDeleteAt.eq("N")).and
        (voteRegist.eq(today)).and(tbVote.voteBeginDt.loe(LocalDateTime.now()))).fetchFirst());
  }


  @Override
  public List<VoteRstrntResponse> getVoteRstrntList() {

    StringTemplate voteRegist = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", tbVote.registDt, "YYYYMMDD");

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String today = date.format(formatter);

    return queryFactory.select(Projections.fields(VoteRstrntResponse.class, tbVote.voteSn.as("voteSsno"), // tbVote.voteKndCode.as("voteKindCode"),
        tbVote.voteSj.as("voteSubject"), // tbVote.annymtyVoteAt.as("anonymityVoteYn"),
        tbVote.voteBeginDt.as("voteBeginDate"), tbVote.voteEndDt.as("voteEndDate"), // tbVote.voteDeleteAt.as("voteDeleteYn"),
        tbQestn.qestnSn.as("qestnSsno"), tbQestn.qestnCn.as("qestnName"), tbQestn.compnoChoiseAt.as("compnoChoiseYn"))).from(tbVote).leftJoin(tbQestn)
        .on(tbVote.voteSn.eq(tbQestn.voteSn)).leftJoin(tbIem).on(tbVote.voteSn.eq(tbIem.voteSn).and(tbQestn.qestnSn.eq(tbIem.qestnSn)))
        .join(rstrntEntity).on(tbIem.iemSn.eq(rstrntEntity.rstrntSn)).where(// tbVote.voteKndCode.eq("LUN").and(tbVote.voteDeleteAt.eq("N")).and
            (voteRegist.eq(today)).and(tbVote.voteBeginDt.loe(LocalDateTime.now())))
        .orderBy(tbVote.voteSn.asc(), tbQestn.qestnSn.asc(), tbIem.iemSn.asc())
        .transform(groupBy(tbVote.voteSn, tbQestn.qestnSn).list(Projections.fields(VoteRstrntResponse.class, tbVote.voteSn.as("voteSsno"),
            // tbVote.voteKndCode.as("voteKindCode"),
            tbVote.voteSj.as("voteSubject"), // tbVote.annymtyVoteAt.as("anonymityVoteYn"),
            tbVote.voteBeginDt.as("voteBeginDate"), tbVote.voteEndDt.as("voteEndDate"),
            // tbVote.voteDeleteAt.as("voteDeleteYn"),
            tbQestn.qestnSn.as("qestnSsno"), tbQestn.qestnCn.as("qestnName"), tbQestn.compnoChoiseAt.as("compnoChoiseYn"),
            list(Projections.fields(VoteRstrntIemResponse.class, tbIem.iemSn.as("iemSsno"), tbIem.iemNm.as("iemName"))).as("voteRstrntIemList"))));
  }

}
