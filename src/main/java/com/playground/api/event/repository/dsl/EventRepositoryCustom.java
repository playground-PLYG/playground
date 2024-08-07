package com.playground.api.event.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.event.entity.EventEntity;
import com.playground.api.event.model.EventRequest;
import com.playground.api.event.model.EventResponse;
import com.playground.api.event.model.EventResultExcelResponse;
import com.playground.api.event.model.EventResultResponse;

public interface EventRepositoryCustom {

  Page<EventResponse> getEventList(EventRequest request, Pageable pageable);

  void modifyEvent(EventEntity request);

  void modifyEndEvent(int eventSn);

  EventEntity getEventDetail(int eventSn);

  List<EventResultResponse> getEventResultList(int eventSn);

  List<EventResultExcelResponse> getEventExcelList(int eventSn);

  void modifyDrwtEvent(int eventSn);
}
