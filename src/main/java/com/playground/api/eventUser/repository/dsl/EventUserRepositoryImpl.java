package com.playground.api.eventUser.repository.dsl;

import static com.playground.api.event.entity.QEventEntity.eventEntity;
import static com.playground.api.event.entity.QEventParticipateEntity.eventParticipateEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.playground.api.eventUser.model.EventUserListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
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
  
}

