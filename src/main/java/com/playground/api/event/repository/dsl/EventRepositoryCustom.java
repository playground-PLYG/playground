package com.playground.api.event.repository.dsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.event.entity.EventEntity;

public interface EventRepositoryCustom {

  Page<EventEntity> getEventList(EventEntity request, Pageable pageable);

  void modifyEvent(EventEntity request);

  void modifyEndEvent(int eventSn);

}
