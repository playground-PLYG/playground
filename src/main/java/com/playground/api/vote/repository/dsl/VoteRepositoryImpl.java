package com.playground.api.vote.repository.dsl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import java.time.LocalDateTime;
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
import com.playground.api.vote.model.VoteSrchRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    return queryFactory.select(tbQestn).from(tbQestn).leftJoin(tbIem).on(tbQestn.voteSn.eq(tbIem.voteSn).and(tbQestn.qestnSn.eq(tbIem.qestnSn)))
        .where(tbQestn.voteSn.eq(voteSsno)).orderBy(tbQestn.voteSn.asc(), tbQestn.qestnSn.asc())
        .transform(groupBy(tbQestn.voteSn, tbQestn.qestnSn).list(Projections.fields(VoteQestnResponse.class, tbQestn.voteSn.as("voteSsno"),
            tbQestn.qestnSn.as("questionSsno"), tbQestn.voteKndCode.as("voteKindCode"), tbQestn.compnoChoiseAt.as("compoundNumberChoiceAlternative"),
            tbQestn.annymtyVoteAt.as("anonymityVoteAlternative"), tbQestn.qestnCn.as("questionContents"), tbQestn.registUsrId.as("registUserId"),
            tbQestn.registDt.as("registDate"), tbQestn.updtUsrId.as("updateUserId"), tbQestn.updtDt.as("updateDate"),
            list(Projections.fields(VoteQestnIemResponse.class, tbIem.iemSn.as("itemSsno"), tbIem.qestnSn.as("questionSno"),
                tbIem.voteSn.as("voteSno"), tbIem.iemIdntfcId.as("itemIdentificationId"), tbIem.iemNm.as("itemName")))
                    .as("voteQestnIemResponseList"))));
  }

  @Override
  public Page<VoteEntity> getVotePageList(VoteSrchRequest reqData, Pageable pageable) {


    List<VoteEntity> content = queryFactory.selectFrom(tbVote)
        .where(buildVoteStatus(reqData.getVoteStatus()), secondCnLike(reqData.getVoteSubject()),
            (tbVote.voteExpsrAt.eq("Y").or(tbVote.registUsrId.eq(reqData.getAnswerUserId()).and(tbVote.voteExpsrAt.eq("N")))))
        .orderBy(tbVote.registDt.asc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery =
        queryFactory.select(tbVote.count()).from(tbVote).where(buildVoteStatus(reqData.getVoteStatus()), secondCnLike(reqData.getVoteSubject()),
            (tbVote.voteExpsrAt.eq("Y").or(tbVote.registUsrId.eq(reqData.getAnswerUserId()).and(tbVote.voteExpsrAt.eq("N")))));

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  /* 동적쿼리를 위한 함수 */
  private BooleanExpression secondCnLike(String secondCn) {
    if (ObjectUtils.isEmpty(secondCn)) {
      return null;
    }

    return tbVote.voteSj.contains(secondCn);
  }


  /*
   * 투표중 : VTI 준비중 : PRP 투표완료 : VTC
   */
  private BooleanExpression buildVoteStatus(String voteStatus) {

    LocalDateTime currentDate = LocalDateTime.now();

    // current_date >= vote_begin_dt AND current_date <= vote_end_dt;
    if ("VTI".equals(voteStatus)) {
      return tbVote.voteBeginDt.loe(currentDate).and(tbVote.voteEndDt.goe(currentDate));
      // current_date < vote_begin_dt
    } else if ("PRP".equals(voteStatus)) {
      return tbVote.voteBeginDt.gt(currentDate);
      // current_date > vote_end_dt
    } else if ("VTC".equals(voteStatus)) {
      return tbVote.voteEndDt.lt(currentDate);
    }

    return null;
  }



  /////////////////////////////////////////////////////////////////////////////////
  ////////////////////// 이하 메소드 사용하는거는 위로 올릴 것 개발 완료후 삭제 예정/////////////////


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
