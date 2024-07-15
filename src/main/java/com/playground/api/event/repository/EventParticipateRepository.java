package com.playground.api.event.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.EventParticipateEntity;
import com.playground.api.event.entity.EventParticipateEntityPK;
import com.playground.api.event.repository.dsl.EventParticipateRepositoryCustom;



public interface EventParticipateRepository
    extends JpaRepository<EventParticipateEntity, EventParticipateEntityPK>, EventParticipateRepositoryCustom {

  List<EventParticipateEntity> findByEventSn(Integer eventSn);
}
