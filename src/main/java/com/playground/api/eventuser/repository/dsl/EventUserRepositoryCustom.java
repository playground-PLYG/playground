package com.playground.api.eventUser.repository.dsl;

import java.util.List;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventUser.model.EventUserDetailResponse;
import com.playground.api.eventUser.model.EventUserListResponse;

public interface EventUserRepositoryCustom {

  List<EventUserListResponse> getEventList(String eventName, String progrsSttus, String mberId);
  
  EventUserDetailResponse getEventUserDetail(Integer eventSerial, String mberId);
  
  List<PointPaymentEntity> getEventPointPaymentList(Integer eventSerial);

}
