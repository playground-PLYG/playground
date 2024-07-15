package com.playground.api.event.repository.dsl;

import static com.playground.api.event.entity.QEventEntity.eventEntity;
import static com.playground.api.event.entity.QEventParticipateEntity.eventParticipateEntity;
import static com.playground.api.member.entity.QMberEntity.mberEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.playground.api.event.entity.EventEntity;
import com.playground.api.event.model.EventResultExcelResponse;
import com.playground.api.event.model.EventResultResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<EventEntity> getEventList(EventEntity req, Pageable pageable) {
    List<EventEntity> event = queryFactory
        .select(Projections.fields(EventEntity.class, eventEntity.eventSn, eventEntity.eventNm, eventEntity.eventSeCodeId, eventEntity.eventBeginDt,
            eventEntity.eventEndDt, eventEntity.registUsrId, eventEntity.registDt, eventEntity.updtUsrId, eventEntity.updtDt,
            new CaseBuilder().when(eventEntity.eventEndDt.lt(LocalDateTime.now())).then("종료").when(eventEntity.eventBeginDt.loe(LocalDateTime.now()))
                .then("진행중").otherwise("예정").as("progrsSttus")))
        .from(eventEntity).where(eventNmLkie(req.getEventNm()), eventSeCodeIdLkie(req.getEventSeCodeId()), progrsSttusSch(req.getProgrsSttus()))
        .orderBy(eventEntity.registDt.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    JPAQuery<Long> countQuery = queryFactory.select(eventEntity.count()).from(eventEntity).where(eventNmLkie(req.getEventNm()));

    return PageableExecutionUtils.getPage(event, pageable, countQuery::fetchOne);
  }

  @Override
  public void modifyEvent(EventEntity req) {
    JPAUpdateClause clause = queryFactory.update(eventEntity).where(eventEntity.eventSn.eq(req.getEventSn()));
    // .set(eventEntity.eventNm, req.getEventNm());
    if (req.getEventNm() != null) { // 이벤트명
      clause.set(eventEntity.eventNm, req.getEventNm());
    }
    if (req.getEventBeginDt() != null) { // 이벤트 시작일시
      clause.set(eventEntity.eventBeginDt, req.getEventBeginDt());
    }
    if (req.getEventEndDt() != null) { // 이벤트 종료일시
      clause.set(eventEntity.eventEndDt, req.getEventEndDt());
    }
    if (req.getEventThumbFileSn() != null) { // 이벤트썸네일파일일련번호
      clause.set(eventEntity.eventThumbFileSn, req.getEventThumbFileSn());
    }
    if (req.getPrzwnerCo() != null) { // 당첨자수
      clause.set(eventEntity.przwnerCo, req.getPrzwnerCo());
    }
    if (req.getEventSeCodeId() != null) { // 이벤트구분코드ID
      clause.set(eventEntity.eventSeCodeId, req.getEventSeCodeId());
    }
    if (req.getDrwtMthdCodeId() != null) { // 추첨방식코드ID
      clause.set(eventEntity.drwtMthdCodeId, req.getDrwtMthdCodeId());
    }
    if (req.getPointPymntMthdCodeId() != null) { // 포인트지급방식코드ID
      clause.set(eventEntity.pointPymntMthdCodeId, req.getPointPymntMthdCodeId());
    }
    if (req.getTotPointValue() != null) { // 총포인트값
      clause.set(eventEntity.totPointValue, req.getTotPointValue());
    }
    if (req.getCntntsCn() != null) { // 컨테츠내용
      clause.set(eventEntity.cntntsCn, req.getCntntsCn());
    }
    if (req.getExpsrAt() != null) { // 노출여부
      clause.set(eventEntity.expsrAt, req.getExpsrAt());
    }
    clause.execute();
  }

  @Override
  public void modifyEndEvent(int eventSn) {
    queryFactory.update(eventEntity).set(eventEntity.eventEndDt, LocalDateTime.now()).where(eventEntity.eventSn.eq(eventSn)).execute();
  }

  @Override
  public EventEntity getEventDetail(int eventSn) {
    // TODO Auto-generated method stub
    return queryFactory
        .select(Projections.fields(EventEntity.class, eventEntity.eventSn, eventEntity.eventNm, eventEntity.eventBeginDt, eventEntity.eventEndDt,
            eventEntity.eventThumbFileSn, eventEntity.przwnerCo, eventEntity.eventSeCodeId, eventEntity.drwtMthdCodeId,
            eventEntity.pointPymntMthdCodeId, eventEntity.totPointValue, eventEntity.cntntsCn, eventEntity.expsrAt, eventEntity.drwtDt))
        .from(eventEntity).where(eventEntity.eventSn.eq(eventSn)).fetchOne();
  }

  @Override
  public List<EventResultResponse> getEventResultList(int eventSn) {
    // TODO Auto-generated method stub
    // List<EventResultResponse> eventResultResponse =

    return queryFactory
        .select(Projections.fields(EventResultResponse.class, eventEntity.eventSn.as("eventSerial"), eventEntity.totPointValue.as("totalPointValue"),
            mberEntity.mberNm.as("memberNm"), mberEntity.mberId.as("memberId"), mberEntity.mberTelno.as("memberTelno"),
            eventParticipateEntity.przwinPointValue.as("przwinPointVal"), eventParticipateEntity.eventPrzwinAt.as("eventPrzwinAlter"),
            eventParticipateEntity.eventPartcptnDt.as("eventPartcptnDate")))
        .from(eventEntity).join(eventParticipateEntity).on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn)).join(mberEntity)
        .on(eventParticipateEntity.mberId.eq(mberEntity.mberId)).where(eventEntity.eventSn.eq(eventSn))
        .orderBy(eventParticipateEntity.przwinPointValue.desc(), eventParticipateEntity.eventPartcptnDt.asc()).fetch();
  }

  @Override
  public List<EventResultExcelResponse> getEventExcelList(int eventSn) {
    return queryFactory
        .select(Projections.fields(EventResultExcelResponse.class, eventEntity.eventNm.as("eventName"), mberEntity.mberNm.as("memberName"),
            mberEntity.mberId.as("memberId"), eventParticipateEntity.przwinPointValue.as("przwinPointVal"), mberEntity.mberTelno.as("memberTelno"),
            eventParticipateEntity.eventPartcptnDt.as("eventPartcptnDate")))
        .from(eventEntity).join(eventParticipateEntity).on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn)).join(mberEntity)
        .on(eventParticipateEntity.mberId.eq(mberEntity.mberId)).where(eventEntity.eventSn.eq(eventSn))
        .orderBy(eventParticipateEntity.przwinPointValue.desc(), eventParticipateEntity.eventPartcptnDt.asc()).fetch();
  }

  /* 이벤트명 조회 동적쿼리 */
  private BooleanExpression eventNmLkie(String eventNm) {
    return StringUtils.isNotBlank(eventNm) ? eventEntity.eventNm.like(eventNm + "%") : null;
  }

  /* 이벤트구분코드 조회 동적쿼리 */
  private BooleanExpression eventSeCodeIdLkie(String eventSeCodeId) {
    return StringUtils.isNotBlank(eventSeCodeId) ? eventEntity.eventSeCodeId.like(eventSeCodeId + "%") : null;
  }

  /* 진행상태 조회 동적쿼리 */
  private BooleanExpression progrsSttusSch(String progrsSttus) {
    if (StringUtils.isNotBlank(progrsSttus)) {
      if (progrsSttus.equals("종료")) {
        return eventEntity.eventEndDt.lt(LocalDateTime.now());
      } else if (progrsSttus.equals("진행중")) {
        return eventEntity.eventBeginDt.loe(LocalDateTime.now()).and(eventEntity.eventEndDt.goe(LocalDateTime.now()));
      } else if (progrsSttus.equals("예정")) {
        return eventEntity.eventBeginDt.gt(LocalDateTime.now());
      }
    }
    return null;
  }

}

