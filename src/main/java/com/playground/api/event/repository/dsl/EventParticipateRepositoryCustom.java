package com.playground.api.event.repository.dsl;

import java.util.List;
import com.playground.api.event.entity.EventParticipateEntity;
import com.playground.api.event.entity.PointPaymentEntity;
import com.querydsl.core.Tuple;

public interface EventParticipateRepositoryCustom {

  List<Tuple> raffleMember(List<PointPaymentEntity> pointList);

  List<EventParticipateEntity> randomMember(int eventSn);

  void prizeUpdate(EventParticipateEntity entity);
}
