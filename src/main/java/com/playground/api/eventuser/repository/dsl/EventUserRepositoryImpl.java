package com.playground.api.eventuser.repository.dsl;

import static com.playground.api.event.entity.QEventEntity.eventEntity;
import static com.playground.api.event.entity.QEventParticipateEntity.eventParticipateEntity;
import static com.playground.api.event.entity.QPointEntity.pointEntity;
import static com.playground.api.event.entity.QPointPaymentEntity.pointPaymentEntity;
import static com.playground.api.member.entity.QMberEntity.mberEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventuser.model.EventParticipationResponse;
import com.playground.api.eventuser.model.EventPrizeWinnerResponse;
import com.playground.api.eventuser.model.EventUserDetailRequest;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListResponse;
import com.playground.api.eventuser.model.PrizeWinnerResponse;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventUserRepositoryImpl implements EventUserRepositoryCustom {

  public static final String EVENT_CODE = "EVNT";
  public static final String END = "END";
  public static final String ING = "ING";

  private final JPAQueryFactory queryFactory;

  /* 이벤트 목록 조회 */
  @Override
  public List<EventUserListResponse> getEventList(String eventName, String progrsSttus, String mberId) {
    LocalDateTime now = LocalDateTime.now();
    NumberExpression<Integer> eventStatusOrder = new CaseBuilder()
        .when(eventEntity.eventEndDt.lt(now)).then(1) // '종료' 상태의 이벤트는 맨 앞
        .when(eventEntity.eventBeginDt.loe(now)).then(1) // '진행중' 상태의 이벤트는 맨 앞
        .otherwise(2);
    
    return queryFactory
        .select(Projections.fields(EventUserListResponse.class,
            eventEntity.eventSn.as("eventSerial"),
            eventEntity.eventNm.as("eventName"),
            eventEntity.eventBeginDt.as("eventBeginDate"),
            eventEntity.eventEndDt.as("eventEndDate"),
            eventEntity.eventThumbFileSn.as("eventThumbFileSn"),
            new CaseBuilder()
                .when(eventEntity.eventEndDt.lt(now))
                .then("종료")
                .when(eventEntity.eventBeginDt.loe(now))
                .then("진행중")
                .otherwise("예정").as("progrsSttus"),
            new CaseBuilder()
                .when(eventParticipateEntity.mberId.isNotNull())
                .then("참여완료")
                .otherwise("미참여").as("participationAt")
        ))
        .from(eventEntity)
        .leftJoin(eventParticipateEntity)
        .on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn).and(eventParticipateEntity.mberId.eq(mberId)))
        .where(eventEntity.expsrAt.eq("Y"), eventNameLike(eventName), eventStatusEq(progrsSttus))
        .orderBy(eventStatusOrder.asc(), eventEntity.eventBeginDt.desc())
        .fetch();
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
      case END:
        return eventEntity.eventEndDt.before(now);
      case ING:
        return (eventEntity.eventEndDt.goe(now));
      default:
        return null;
    }
  }
  
  /* 이벤트 목록 페이징 조회 */
  @Override
  public Page<EventUserListResponse> getEventPageList(String eventName, String progrsSttus, String mberId, Pageable pageable) {
    LocalDateTime now = LocalDateTime.now();
    NumberExpression<Integer> eventStatusOrder = new CaseBuilder()
        .when(eventEntity.eventBeginDt.loe(now)).then(1) 
        .when(eventEntity.eventEndDt.lt(now)).then(1) 
        .otherwise(2);
    
    List<EventUserListResponse> result =   queryFactory
        .select(Projections.fields(EventUserListResponse.class,
            eventEntity.eventSn.as("eventSerial"),
            eventEntity.eventNm.as("eventName"),
            eventEntity.eventBeginDt.as("eventBeginDate"),
            eventEntity.eventEndDt.as("eventEndDate"),
            eventEntity.eventThumbFileSn.as("eventThumbFileSn"),
            new CaseBuilder()
                .when(eventEntity.eventEndDt.lt(now))
                .then("종료")
                .when(eventEntity.eventBeginDt.loe(now))
                .then("진행중")
                .otherwise("예정").as("progrsSttus"),
            new CaseBuilder()
                .when(eventParticipateEntity.mberId.isNotNull())
                .then("참여완료")
                .otherwise("미참여").as("participationAt")
        ))
        .from(eventEntity)
        .leftJoin(eventParticipateEntity)
        .on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn).and(eventParticipateEntity.mberId.eq(mberId)))
        .where(eventEntity.expsrAt.eq("Y"), eventNameLike(eventName), eventStatusEq(progrsSttus))
        .orderBy(eventStatusOrder.asc(), eventEntity.eventBeginDt.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
        .fetch();
    
    JPAQuery<Long> countQuery = queryFactory.select(eventEntity.count()).from(eventEntity)
        .leftJoin(eventParticipateEntity)
        .on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn).and(eventParticipateEntity.mberId.eq(mberId)))
        .where(eventEntity.expsrAt.eq("Y"), eventNameLike(eventName), eventStatusEq(progrsSttus));
    
    return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
  }

  /* 이벤트 상세 조회 */
  @Override
  public EventUserDetailResponse getEventUserDetail(Integer eventSerial, String mberId) {

    return queryFactory
        .select(Projections.fields(EventUserDetailResponse.class, 
            eventEntity.eventSn.as("eventSerial"), 
            eventEntity.eventNm.as("eventName"),
            eventEntity.cntntsCn.as("contents"), 
            eventEntity.eventBeginDt.as("eventBeginDate"), 
            eventEntity.eventEndDt.as("eventEndDate"),
            eventEntity.eventSeCodeId.as("eventSectionCodeId"), 
            eventEntity.drwtMthdCodeId.as("drwtMethodCodeId"),
            new CaseBuilder()
              .when(eventParticipateEntity.mberId.isNotNull())
              .then("Y").otherwise("N").as("participationAt")))
        .from(eventEntity).leftJoin(eventParticipateEntity)
        .on(eventEntity.eventSn.eq(eventParticipateEntity.eventSn).and(eventParticipateEntity.mberId.eq(mberId)))
        .where(eventEntity.eventSn.eq(eventSerial)).fetchOne();
  }

  /* 이벤트 지급 정보 조회 */
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

  /* 이벤트 추첨방식 조회 */
  @Override
  public String getDrwtMthdCode(EventUserDetailRequest req) {
    return queryFactory.select(eventEntity.drwtMthdCodeId).from(eventEntity).where(eventEntity.eventSn.eq(req.getEventSerial())).fetchOne();
  }

  /* 참여형 랜덤 이벤트 참여 */
  @Override
  public EventParticipationResponse addRandParticipation(EventUserDetailRequest req, String mberId) {

    EventParticipationResponse response = new EventParticipationResponse();
    
    // 잔여 포인트 계산
    Integer remainingPoints = queryFactory
        .select(eventEntity.totPointValue.subtract(JPAExpressions.select(eventParticipateEntity.przwinPointValue.sum().coalesce(0))
            .from(eventParticipateEntity).where(eventParticipateEntity.eventSn.eq(req.getEventSerial()))))
        .from(eventEntity).where(eventEntity.eventSn.eq(req.getEventSerial())).fetchOne();

    // 포인트 지급 단위 값 계산
    Integer pointPymntUnitValue = 
        queryFactory.select(pointPaymentEntity.pointPymntUnitValue)
        .from(pointPaymentEntity)
        .where(pointPaymentEntity.eventSn.eq(req.getEventSerial())
            .and(pointPaymentEntity.pointPymntUnitValue.loe(remainingPoints)))
        .orderBy(NumberExpression.random().asc())
        .limit(1)
        .fetchFirst();

    if (pointPymntUnitValue == null) {
      pointPymntUnitValue = 0;
    }

    // 당첨여부
    String eventPrzwinAt = (pointPymntUnitValue == 0) ? "N" : "Y";
    
    // 참여 insert
    queryFactory.insert(eventParticipateEntity)
        .columns(
            eventParticipateEntity.eventSn, 
            eventParticipateEntity.mberId, 
            eventParticipateEntity.przwinPointValue,
            eventParticipateEntity.eventPrzwinAt, 
            eventEntity.registUsrId, 
            eventEntity.updtUsrId)
        .values(
            req.getEventSerial(), 
            mberId, 
            pointPymntUnitValue, 
            eventPrzwinAt, 
            mberId, 
            mberId)
        .execute();

    // 당첨 시 point 적립
    if (eventPrzwinAt.equals("Y")) {
      queryFactory.insert(pointEntity)
          .columns(
              pointEntity.mberId, 
              pointEntity.pointValue, 
              pointEntity.validDt, 
              pointEntity.refrnId, 
              pointEntity.refrnSeCodeId,
              pointEntity.registUsrId, 
              pointEntity.updtUsrId)
          .values(
              mberId, 
              pointPymntUnitValue, 
              LocalDateTime.now().plusYears(1), 
              req.getEventSerial(), 
              EVENT_CODE, 
              mberId, 
              mberId)
          .execute();
    }
    
    response.setEventPrizeAt(eventPrzwinAt);
    response.setPrzwinPointValue(pointPymntUnitValue);
    
    return response;

  }

  /* 참여형 순차지급 이벤트 참여 */
  @Override
  public EventParticipationResponse addFrscParticipation(EventUserDetailRequest req, String mberId) {

    EventParticipationResponse response = new EventParticipationResponse();
    
    // 사용자 순위
    Long rank = 
        queryFactory.select(eventParticipateEntity.count().add(1))
        .from(eventParticipateEntity)
        .where(eventParticipateEntity.eventSn.eq(req.getEventSerial()))
        .fetchOne();

    // 이벤트 지급정보
    List<Tuple> results = queryFactory
        .select(
            pointPaymentEntity.pointPymntSn, 
            pointPaymentEntity.fixingPointPayrCo, 
            pointPaymentEntity.fixingPointValue)
        .from(pointPaymentEntity)
        .where(pointPaymentEntity.eventSn.eq(req.getEventSerial()))
        .orderBy(pointPaymentEntity.pointPymntSn.asc())
        .fetch();

    Tuple validResult = null;
    int participants = 0; // 누적 참여자 수
    for (Tuple result : results) {
      Integer fixingPointPayrCo = result.get(pointPaymentEntity.fixingPointPayrCo); // 참여자 수

      participants += fixingPointPayrCo;

      if (participants >= rank) {
        validResult = result;
        break;
      }
    }

    if (validResult != null) {
      Integer fixingPointValue = validResult.get(2, Integer.class); // 순위에 해당하는 fixingPointValue

      // 참여(당첨) insert
      queryFactory.insert(eventParticipateEntity)
          .columns(
              eventParticipateEntity.eventSn, 
              eventParticipateEntity.mberId, 
              eventParticipateEntity.przwinPointValue,
              eventParticipateEntity.eventPrzwinAt, 
              eventEntity.registUsrId, 
              eventEntity.updtUsrId)
          .values(
              req.getEventSerial(), 
              mberId, 
              fixingPointValue, 
              "Y", 
              mberId, 
              mberId)
          .execute();

      // 포인트 지급
      queryFactory.insert(pointEntity)
          .columns(
              pointEntity.mberId, 
              pointEntity.pointValue, 
              pointEntity.validDt, 
              pointEntity.refrnId, 
              pointEntity.refrnSeCodeId,
              pointEntity.registUsrId, 
              pointEntity.updtUsrId)
          .values(
              mberId, 
              fixingPointValue, 
              LocalDateTime.now().plusYears(1), 
              req.getEventSerial(), 
              EVENT_CODE, 
              mberId, 
              mberId)
          .execute();
      
      response.setEventPrizeAt("Y");
      response.setPrzwinPointValue(fixingPointValue);
    } else {
      // 참여(당첨X) insert
      queryFactory.insert(eventParticipateEntity)
          .columns(
              eventParticipateEntity.eventSn, 
              eventParticipateEntity.mberId, 
              eventParticipateEntity.przwinPointValue,
              eventParticipateEntity.eventPrzwinAt,
              eventEntity.registUsrId, 
              eventEntity.updtUsrId)
          .values(
              req.getEventSerial(), 
              mberId, 
              0, 
              "N", 
              mberId, 
              mberId)
          .execute();
      
      response.setEventPrizeAt("N");
      response.setPrzwinPointValue(0);
    }
    
    return response;
  }

  /* 이벤트 응모 */
  @Override
  public void addEventRaffle(EventUserDetailRequest req, String mberId) {
    queryFactory.insert(eventParticipateEntity)
        .columns(
            eventParticipateEntity.eventSn, 
            eventParticipateEntity.mberId, 
            eventEntity.registUsrId, 
            eventEntity.updtUsrId)
        .values(
            req.getEventSerial(), 
            mberId, 
            mberId, 
            mberId)
        .execute();
  }

  /* 응모형 이벤트 당첨자 조회 */
  @Override
  public List<PrizeWinnerResponse> getEntryEventWinningList(Integer eventSn, String mberId) {
    return queryFactory
        .select(Projections.fields(PrizeWinnerResponse.class, 
            mberEntity.mberNm.as("memberName"), 
            eventParticipateEntity.mberId.as("memberId"),
            eventParticipateEntity.przwinPointValue.as("pointPaymentUnitValue")))
        .from(eventParticipateEntity).join(eventEntity).on(eventParticipateEntity.eventSn.eq(eventEntity.eventSn)).join(mberEntity)
        .on(eventParticipateEntity.mberId.eq(mberEntity.mberId))
        .where(eventParticipateEntity.eventPrzwinAt.eq("Y").and(eventEntity.eventSn.eq(eventSn)))
        .orderBy(eventParticipateEntity.przwinPointValue.desc(), eventParticipateEntity.eventPartcptnDt.asc())
        .fetch();
  }

  /* 응모형 이벤트 사용자 당첨여부 */
  @Override
  public EventPrizeWinnerResponse getPrizeAt(Integer eventSn, String mberId) {

    return queryFactory
        .select(Projections.fields(EventPrizeWinnerResponse.class,
            eventParticipateEntity.eventPrzwinAt.as("eventPrizeAt"),
            eventParticipateEntity.przwinPointValue.as("przwinPointValue"),
            mberEntity.mberNm.as("memberName") 
        ))
        .from(eventParticipateEntity)
        .leftJoin(mberEntity) 
        .on(eventParticipateEntity.mberId.eq(mberEntity.mberId))
        .where(eventParticipateEntity.eventSn.eq(eventSn)
            .and(eventParticipateEntity.mberId.eq(mberId)))
        .fetchOne();
  }
}

