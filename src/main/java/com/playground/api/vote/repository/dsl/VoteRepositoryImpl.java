package com.playground.api.vote.repository.dsl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.playground.api.vote.entity.QVoteEntity;
import com.playground.api.vote.entity.QVoteQestnEntity;
import com.playground.api.vote.entity.QVoteQestnIemEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.entity.VoteQestnEntity;
import com.playground.api.vote.model.VoteQestnIemResponse;
import com.playground.api.vote.model.VoteQestnResponse;
import com.playground.api.vote.model.VoteRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor

public class VoteRepositoryImpl implements VoteRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  QVoteEntity tbVote = QVoteEntity.voteEntity;
  QVoteQestnEntity tbQestn = QVoteQestnEntity.voteQestnEntity;
  QVoteQestnIemEntity tbIem = QVoteQestnIemEntity.voteQestnIemEntity;

  @Override
  public List<VoteQestnResponse> getVoteQestnDetail(Integer voteSsno) {
    return queryFactory.select(tbQestn).from(tbQestn)
        .leftJoin(tbIem)
        .on(tbQestn.voteSn.eq(tbIem.voteSn)
            .and(tbQestn.qestnSn.eq(tbIem.qestnSn)))
        .where(tbQestn.voteSn.eq(voteSsno))
        .orderBy(tbQestn.voteSn.asc(), tbQestn.qestnSn.asc())
        .transform(groupBy(tbQestn.voteSn, tbQestn.qestnSn)
            .list(
                Projections.fields(VoteQestnResponse.class, 
                    tbQestn.voteSn.as("voteSsno"),
                    tbQestn.qestnSn.as("questionSsno"), 
                    tbQestn.voteKndCode.as("voteKindCode"), 
                    tbQestn.compnoChoiseAt.as("compoundNumberChoiceAlternative"),
                    tbQestn.annymtyVoteAt.as("anonymityVoteAlternative"), 
                    tbQestn.qestnCn.as("questionContents"), 
                    tbQestn.registUsrId.as("registUserId"),
                    tbQestn.registDt.as("registDate"), 
                    tbQestn.updtUsrId.as("updateUserId"), 
                    tbQestn.updtDt.as("updateDate"),
            list(
                Projections.fields(VoteQestnIemResponse.class, 
                    tbIem.iemSn.as("itemSsno"), 
                    tbIem.qestnSn.as("questionSno"),
                    tbIem.voteSn.as("voteSno"), 
                    tbIem.iemIdntfcId.as("itemIdentificationId"), 
                    tbIem.iemNm.as("itemName"))).as("voteQestnIemResponseList")
            )
                )
            );
  }



  /////////////////////////////////////////////////////////////////////////////////
  ////////////////////// 이하 메소드 사용하는거는 위로 올릴 것 개발 완료후 삭제 예정/////////////////


  @Override
  public Page<VoteEntity> getVotePageList(VoteRequest reqData, Pageable pageable) {
    List<VoteEntity> content = queryFactory.selectFrom(tbVote).where(// firstCnLike(reqData.getVoteKindCode()),
        secondCnLike(reqData.getVoteSubject()),
        // thirdCnEq(reqData.getAnonymityVoteAlternative()),
        fourthCnEq(reqData.getVoteBeginDate(), reqData.getVoteEndDate())).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery = queryFactory.select(tbVote.count()).from(tbVote).where(
        // firstCnLike(reqData.getVoteKindCode()),
        secondCnLike(reqData.getVoteSubject()),
        // thirdCnEq(reqData.getAnonymityVoteAlternative()),
        fourthCnEq(reqData.getVoteBeginDate(), reqData.getVoteEndDate()));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  /* 동적쿼리를 위한 함수 */
  private BooleanExpression secondCnLike(String secondCn) {
    if (ObjectUtils.isEmpty(secondCn)) {
      return null;
    }

    return tbVote.voteSj.contains(secondCn);
  }

  private BooleanExpression fourthCnEq(String startDate, String finDate) {
    boolean sdBoo = true;
    boolean fdBoo = true;
    if (ObjectUtils.isEmpty(startDate)) {
      sdBoo = false;
    }

    if (ObjectUtils.isEmpty(finDate)) {
      fdBoo = false;
    }

    if (!sdBoo && !fdBoo) {
      return null;
    }

    if (sdBoo && !fdBoo) {
      // 시작일자만 있고 종료일자 없을때
      LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);// 시작일자
      return tbVote.voteBeginDt.goe(LocalDateTime.of(startLocalDate, LocalTime.MIN));
    } else if (!sdBoo && fdBoo) {
      // 시작일자 없고 종료일자만 있을때
      LocalDate finLocalDate = LocalDate.parse(finDate, DateTimeFormatter.ISO_DATE);// 종료일자
      return tbVote.voteEndDt.loe(LocalDateTime.of(finLocalDate, LocalTime.MAX).withNano(0));
    } else {
      // 시작일자와 종료일자 전부 있을때
      LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);// 시작일자
      LocalDate finLocalDate = LocalDate.parse(finDate, DateTimeFormatter.ISO_DATE);// 종료일자
      BooleanExpression isGoeStartDate = tbVote.voteBeginDt.goe(LocalDateTime.of(startLocalDate, LocalTime.MIN));
      BooleanExpression isLoeEndDate = tbVote.voteEndDt.loe(LocalDateTime.of(finLocalDate, LocalTime.MAX).withNano(0));
      return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }
  }

  @Override
  public Long updateByIdForVote(VoteRequest reqData) {
    LocalDateTime nowDateTime = LocalDateTime.now();
    return queryFactory.update(tbVote)// .set(voteEntity.voteDeleteAt, reqData.getVoteDeleteAlternative())
        .set(tbVote.updtDt, nowDateTime).where(tbVote.voteSn.eq(reqData.getVoteSsno())).execute();
  }

  @Override
  public Long deleteByVoteSnForQestn(Integer voteSsno) {
    return queryFactory.delete(tbQestn).where(tbQestn.voteSn.eq(voteSsno)).execute();
  }

  @Override
  public Long deleteByVoteSnForVoteIem(Integer voteSsno) {
    return queryFactory.delete(tbIem).where(tbIem.voteSn.eq(voteSsno)).execute();
  }

  @Override
  public Long deleteBySsnoForVoteIem(Integer voteSsno, Integer questionSsno) {
    return queryFactory.delete(tbIem).where(tbIem.voteSn.eq(voteSsno).and(tbIem.qestnSn.eq(questionSsno))).execute();
  }

  @Override
  public List<VoteQestnEntity> getQestnList(Integer voteSsno) {
    return queryFactory.selectFrom(tbQestn).where(tbQestn.voteSn.eq(voteSsno)).fetch();
  }

  @Override
  public Long deleteBySsnoForQestn(Integer voteSsno, Integer questionSsno) {
    return queryFactory.delete(tbQestn).where(tbQestn.voteSn.eq(voteSsno).and(tbQestn.qestnSn.eq(questionSsno))).execute();
  }

}
