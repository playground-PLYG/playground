package com.playground.api.event.repository.dsl;

import static com.playground.api.event.entity.QEventParticipateEntity.eventParticipateEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.playground.api.event.entity.EventParticipateEntity;
import com.playground.api.event.entity.PointPaymentEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventParticipateRepositoryImpl implements EventParticipateRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Tuple> raffleMember(List<PointPaymentEntity> pointList) {

    return null;
  }

  @Override
  public List<EventParticipateEntity> randomMember(int eventSn) {
    // TODO Auto-generated method stub
    return queryFactory.select(Projections.fields(EventParticipateEntity.class, eventParticipateEntity.eventSn, eventParticipateEntity.mberId))
        .from(eventParticipateEntity).where(eventParticipateEntity.eventSn.eq(eventSn)).orderBy(NumberExpression.random().asc()).fetch();
  }

  @Override
  public void prizeUpdate(EventParticipateEntity entity) {
    // TODO Auto-generated method stub
    queryFactory.update(eventParticipateEntity).set(eventParticipateEntity.przwinPointValue, entity.getPrzwinPointValue())
        .set(eventParticipateEntity.eventPrzwinAt, "Y").set(eventParticipateEntity.eventPartcptnDt, LocalDateTime.now())
        .where(eventParticipateEntity.eventSn.eq(entity.getEventSn()).and(eventParticipateEntity.mberId.eq(entity.getMberId()))).execute();
  }

}

