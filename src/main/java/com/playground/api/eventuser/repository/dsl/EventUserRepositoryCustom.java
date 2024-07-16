package com.playground.api.eventuser.repository.dsl;

import java.util.List;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListResponse;

public interface EventUserRepositoryCustom {

  List<EventUserListResponse> getEventList(String eventName, String progrsSttus, String mberId);
  
  EventUserDetailResponse getEventUserDetail(Integer eventSerial, String mberId);
  
  List<PointPaymentEntity> getEventPointPaymentList(Integer eventSerial);

}
