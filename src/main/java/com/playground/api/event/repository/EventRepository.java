package com.playground.api.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {

}
