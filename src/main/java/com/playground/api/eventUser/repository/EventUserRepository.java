package com.playground.api.eventUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.EventEntity;
import com.playground.api.eventUser.repository.dsl.EventUserRepositoryCustom;

public interface EventUserRepository extends JpaRepository<EventEntity, Integer>, EventUserRepositoryCustom {

}
