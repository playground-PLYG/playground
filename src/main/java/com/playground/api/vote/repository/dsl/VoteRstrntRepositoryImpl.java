package com.playground.api.vote.repository.dsl;

import static com.playground.api.restaurant.entity.QRstrntEntity.rstrntEntity;
import static com.playground.api.vote.entity.QQestnEntity.qestnEntity;
// 해당 클래스에서 사용할 Q클래스 테이블 설정
import static com.playground.api.vote.entity.QVoteEntity.voteEntity;
import static com.playground.api.vote.entity.QVoteIemEntity.voteIemEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Repository;
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

  @Override
  public Integer getVoteRstrntCount() {
    StringTemplate voteRegist = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", voteEntity.registDt, "YYYYMMDD");

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String today = date.format(formatter);

    return Math.toIntExact(queryFactory.select(voteEntity.count()).from(voteEntity).where(voteEntity.voteKndCode.eq("LUN")
        .and(voteEntity.voteDeleteAt.eq("N")).and(voteRegist.eq(today)).and(voteEntity.voteBeginDt.loe(LocalDateTime.now()))).fetchFirst());
  }


  @Override
  public List<VoteRstrntResponse> getVoteRstrntList() {

    StringTemplate voteRegist = Expressions.stringTemplate("TO_CHAR({0}, '{1s}')", voteEntity.registDt, "YYYYMMDD");

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String today = date.format(formatter);

    return queryFactory
        .select(Projections.fields(VoteRstrntResponse.class, voteEntity.voteSn.as("voteSsno"), voteEntity.voteKndCode.as("voteKindCode"),
            voteEntity.voteSj.as("voteSubject"), voteEntity.annymtyVoteAt.as("anonymityVoteYn"), voteEntity.voteBeginDt.as("voteBeginDate"),
            voteEntity.voteEndDt.as("voteEndDate"), voteEntity.voteDeleteAt.as("voteDeleteYn"), qestnEntity.qestnSn.as("qestnSsno"),
            qestnEntity.qestnCn.as("qestnName"), qestnEntity.compnoChoiseAt.as("compnoChoiseYn")))
        .from(voteEntity).leftJoin(qestnEntity).on(voteEntity.voteSn.eq(qestnEntity.voteSn)).leftJoin(voteIemEntity)
        .on(voteEntity.voteSn.eq(voteIemEntity.voteSn).and(qestnEntity.qestnSn.eq(voteIemEntity.qestnSn))).join(rstrntEntity)
        .on(voteIemEntity.iemSn.eq(rstrntEntity.rstrntSn))
        .where(voteEntity.voteKndCode.eq("LUN").and(voteEntity.voteDeleteAt.eq("N")).and(voteRegist.eq(today))
            .and(voteEntity.voteBeginDt.loe(LocalDateTime.now())))
        .orderBy(voteEntity.voteSn.asc(), qestnEntity.qestnSn.asc(), voteIemEntity.iemSn.asc())
        .transform(groupBy(voteEntity.voteSn, qestnEntity.qestnSn).list(Projections.fields(VoteRstrntResponse.class, voteEntity.voteSn.as("voteSsno"),
            voteEntity.voteKndCode.as("voteKindCode"), voteEntity.voteSj.as("voteSubject"), voteEntity.annymtyVoteAt.as("anonymityVoteYn"),
            voteEntity.voteBeginDt.as("voteBeginDate"), voteEntity.voteEndDt.as("voteEndDate"), voteEntity.voteDeleteAt.as("voteDeleteYn"),
            qestnEntity.qestnSn.as("qestnSsno"), qestnEntity.qestnCn.as("qestnName"), qestnEntity.compnoChoiseAt.as("compnoChoiseYn"),
            list(Projections.fields(VoteRstrntIemResponse.class, voteIemEntity.iemSn.as("iemSsno"), voteIemEntity.iemNm.as("iemName")))
                .as("voteRstrntIemList"))));
  }

}
