package com.playground.api.eventuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.EventEntity;
import com.playground.api.eventuser.repository.dsl.EventUserRepositoryCustom;

public interface EventUserRepository extends JpaRepository<EventEntity, Integer>, EventUserRepositoryCustom {

}
