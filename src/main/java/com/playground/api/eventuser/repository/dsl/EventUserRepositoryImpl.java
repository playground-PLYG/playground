package com.playground.api.eventuser.repository.dsl;

import static com.playground.api.event.entity.QEventEntity.eventEntity;
import static com.playground.api.event.entity.QEventParticipateEntity.eventParticipateEntity;
import static com.playground.api.event.entity.QPointPaymentEntity.pointPaymentEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventuser.model.EventUserDetailRequest;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventUserRepositoryImpl implements EventUserRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<EventUserListResponse> getEventList(String eventName, String progrsSttus, String mberId) {
    return queryFactory
        .select(Projections.fields(EventUserListResponse.class, eventEntity.eventSn.as("eventSerial"), eventEntity.eventNm.as("eventName"),
            eventEntity.eventBeginDt.as("eventBeginDate"), eventEntity.eventEndDt.as("eventEndDate"),
            eventEntity.eventThumbFileSn.as("eventThumbFileSn"),
            new CaseBuilder().when(eventEntity.eventEndDt.lt(LocalDateTime.now())).then("종료").when(eventEntity.eventBeginDt.loe(LocalDateTime.now()))
                .then("진행중").otherwise("예정").as("progrsSttus"),
            new CaseBuilder().when(eventParticipateEntity.mberId.isNotNull()).then("참여완료").otherwise("미참여").as("participationAt")))
        .from(eventEntity).leftJoin(eventParticipateEntity)
        .on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn).and(eventParticipateEntity.mberId.eq(mberId)))
        .where(eventEntity.expsrAt.eq("Y"), eventNameLike(eventName), eventStatusEq(progrsSttus)).orderBy(eventEntity.eventBeginDt.desc()).fetch();
  }

  private BooleanExpression eventNameLike(String eventName) {
    return StringUtils.isNotBlank(eventName) ? eventEntity.eventNm.like(eventName + "%") : null;
  }

  private BooleanExpression eventStatusEq(String progrsSttus) {
    LocalDateTime now = LocalDateTime.now();
    if (progrsSttus == null) {
      return null;
    }
    switch (progrsSttus) {
      case "e":
        return eventEntity.eventEndDt.before(now);
      case "i":
        return (eventEntity.eventEndDt.goe(now));
      default:
        return null;
    }
  }

  @Override
  public EventUserDetailResponse getEventUserDetail(Integer eventSerial, String mberId) {

    return queryFactory
        .select(Projections.fields(EventUserDetailResponse.class, eventEntity.eventSn.as("eventSerial"), eventEntity.eventNm.as("eventName"),
            eventEntity.cntntsCn.as("contents"), eventEntity.eventBeginDt.as("eventBeginDate"), eventEntity.eventEndDt.as("eventEndDate"),
            eventEntity.eventSeCodeId.as("eventSectionCodeId"), eventEntity.drwtMthdCodeId.as("drwtMethodCodeId"),
            new CaseBuilder().when(eventParticipateEntity.mberId.isNotNull()).then("Y").otherwise("N").as("participationAt")))
        .from(eventEntity).leftJoin(eventParticipateEntity)
        .on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn).and(eventParticipateEntity.mberId.eq(mberId)))
        .where(eventEntity.eventSn.eq(eventSerial)).fetchOne();
  }

  @Override
  public List<PointPaymentEntity> getEventPointPaymentList(Integer eventSerial) {

    return queryFactory.selectFrom(pointPaymentEntity).where(eventSerialEq(eventSerial)).fetch();
  }

  private BooleanExpression eventSerialEq(Integer eventSerial) {
    if (eventSerial == null) {
      return null;
    }
    return pointPaymentEntity.eventSn.eq(eventSerial);
  }

  @Override
  public String getDrwtMthdCode(EventUserDetailRequest req) {

    return queryFactory.select(eventEntity.drwtMthdCodeId).from(eventEntity).where(eventEntity.eventSn.eq(req.getEventSerial())).fetchOne();
  }

  @Override
  public void addRandParticipation(EventUserDetailRequest req, String mberId) {

    // 잔여 포인트 계산
    Integer remainingPoints = queryFactory
        .select(eventEntity.totPointValue.subtract(JPAExpressions.select(eventParticipateEntity.przwinPointValue.sum().coalesce(0))
            .from(eventParticipateEntity).where(eventParticipateEntity.eventSn.eq(req.getEventSerial()))))
        .from(eventEntity).where(eventEntity.eventSn.eq(req.getEventSerial())).fetchOne();

    // 포인트 지급 단위 값 계산
    Integer pointPymntUnitValue = queryFactory.select(pointPaymentEntity.pointPymntUnitValue).from(pointPaymentEntity)
        .where(pointPaymentEntity.eventSn.eq(req.getEventSerial()).and(pointPaymentEntity.pointPymntUnitValue.loe(remainingPoints)))
        .orderBy(NumberExpression.random().asc()).limit(1).fetchFirst();

    if (pointPymntUnitValue == null) {
      pointPymntUnitValue = 0;
    }

    queryFactory.insert(eventParticipateEntity)
        .columns(eventParticipateEntity.eventSn, eventParticipateEntity.mberId, eventParticipateEntity.przwinPointValue,
            eventParticipateEntity.eventPrzwinAt, eventEntity.registUsrId, eventEntity.updtUsrId)
        .values(req.getEventSerial(), mberId, pointPymntUnitValue, (pointPymntUnitValue == 0 ? "N" : "Y"), mberId, mberId).execute();
    
  }

  @Override
  public void addFrscParticipation(EventUserDetailRequest req, String mberId) {

  }

}

