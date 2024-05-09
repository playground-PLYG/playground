package com.playground.api.vote.repository.dsl;

//해당 클래스에서 사용할 Q클래스 테이블 설정
import static com.playground.api.vote.entity.QQestnEntity.qestnEntity;
import static com.playground.api.vote.entity.QVoteEntity.voteEntity;
import static com.playground.api.vote.entity.QVoteIemEntity.voteIemEntity;
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
import com.playground.api.vote.entity.QestnEntity;
import com.playground.api.vote.entity.VoteEntity;
import com.playground.api.vote.entity.VoteIemEntity;
import com.playground.api.vote.model.QestnResponse;
import com.playground.api.vote.model.VoteIemResponse;
import com.playground.api.vote.model.VoteRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class VoteRepositoryImpl implements VoteRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  //QVoteEntity tbVote = QVoteEntity.voteEntity;
  //QQestnEntity tbQestn = QQestnEntity.qestnEntity;

  @Override
  public Page<VoteEntity> getVotePageList(VoteRequest reqData, Pageable pageable) {
    List<VoteEntity> content = queryFactory
        .selectFrom(voteEntity)
        .where(firstCnLike(reqData.getVoteKindCode()),
               secondCnLike(reqData.getVoteSubject()),
               thirdCnEq(reqData.getAnonymityVoteAlternative()),
               fourthCnEq(reqData.getVoteBeginDate(), reqData.getVoteEndDate()))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
    
    JPAQuery<Long> countQuery = queryFactory
        .select(voteEntity.count())
        .from(voteEntity)
        .where(firstCnLike(reqData.getVoteKindCode()),
               secondCnLike(reqData.getVoteSubject()),
               thirdCnEq(reqData.getAnonymityVoteAlternative()),
               fourthCnEq(reqData.getVoteBeginDate(), reqData.getVoteEndDate()));
    
    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  @Override
  public List<QestnResponse> getQestnDetail(Integer voteSsno, Integer questionSsno) {
    return queryFactory.select(qestnEntity)
                .from(qestnEntity)
                .leftJoin(voteIemEntity).on(qestnEntity.voteSn.eq(voteIemEntity.voteSn).and(qestnEntity.qestnSn.eq(voteIemEntity.qestnSn)))
                .where(qestnEntity.qestnSn.eq(questionSsno).and(qestnEntity.voteSn.eq(voteSsno)))
                .orderBy(qestnEntity.voteSn.asc(), qestnEntity.qestnSn.asc())
                .transform(groupBy(qestnEntity.voteSn, qestnEntity.qestnSn).list(
                    Projections.fields(QestnResponse.class, 
                        qestnEntity.voteSn.as("voteSsno")
                        ,qestnEntity.qestnSn.as("questionSsno")
                        ,qestnEntity.qestnCn.as("questionContents")
                        ,qestnEntity.compnoChoiseAt.as("compoundNumberChoiceAlternative")
                        ,list(Projections.fields(VoteIemResponse.class, 
                                voteIemEntity.iemId.as("itemId")
                                ,voteIemEntity.iemNm.as("itemName")
                                )).as("voteIemResponseList")
                        )));
  }
  
  @Override
  public QestnResponse addQestnDetail(QestnEntity requestEntity) {
    Long resultVal = queryFactory.insert(qestnEntity)
                                  .columns(qestnEntity.voteSn, qestnEntity.qestnCn, qestnEntity.compnoChoiseAt, qestnEntity.registUsrId, qestnEntity.updtUsrId)
                                  .values(requestEntity.getVoteSn(),
                                          requestEntity.getQestnCn(),
                                          requestEntity.getCompnoChoiseAt(),
                                          "anno",
                                          "anno"
                                        ).execute();

    log.debug("reseultVal ::::::::::::::::::::: {}", resultVal);

    // QestnResponse 객체에 questionSsno, voteSsno 세팅해서 return 해주기
    return new QestnResponse();
  }
  
  @Override
  public Integer addVoteIemDetail(VoteIemEntity voteIemEntity) {
    // TODO Auto-generated method stub
    return null;
  }

  /* 동적쿼리를 위한 함수 */
  private BooleanExpression firstCnLike(String firstCn) {
    if (ObjectUtils.isEmpty(firstCn)) {
      return null;
    }

    // return voteEntity.voteKndCode.like("%" + fstCn + "%");
    return voteEntity.voteKndCode.contains(firstCn);
  }

  private BooleanExpression secondCnLike(String secondCn) {
    if (ObjectUtils.isEmpty(secondCn)) {
      return null;
    }

    return voteEntity.voteSj.contains(secondCn);
  }

  private BooleanExpression thirdCnEq(String thirdCn) {
    if (ObjectUtils.isEmpty(thirdCn)) {
      return null;
    }

    return voteEntity.annymtyVoteAt.eq(thirdCn);
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
      return voteEntity.voteBeginDt.goe(LocalDateTime.of(startLocalDate, LocalTime.MIN));
    } else if (!sdBoo && fdBoo) {
      // 시작일자 없고 종료일자만 있을때
      LocalDate finLocalDate = LocalDate.parse(finDate, DateTimeFormatter.ISO_DATE);// 종료일자
      return voteEntity.voteEndDt.loe(LocalDateTime.of(finLocalDate, LocalTime.MAX).withNano(0));
    } else {
      // 시작일자와 종료일자 전부 있을때
      LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);// 시작일자
      LocalDate finLocalDate = LocalDate.parse(finDate, DateTimeFormatter.ISO_DATE);// 종료일자
      BooleanExpression isGoeStartDate = voteEntity.voteBeginDt.goe(LocalDateTime.of(startLocalDate, LocalTime.MIN));
      BooleanExpression isLoeEndDate = voteEntity.voteEndDt.loe(LocalDateTime.of(finLocalDate, LocalTime.MAX).withNano(0));
      return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }
  }



}
