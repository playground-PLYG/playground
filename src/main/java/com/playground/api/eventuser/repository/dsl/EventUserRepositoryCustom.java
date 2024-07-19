package com.playground.api.eventuser.repository.dsl;

import java.util.List;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventuser.model.EventPrizeWinnerResponse;
import com.playground.api.eventuser.model.EventUserDetailRequest;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListResponse;
import com.playground.api.eventuser.model.PrizeWinnerResponse;

public interface EventUserRepositoryCustom {

  List<EventUserListResponse> getEventList(String eventName, String progrsSttus, String mberId);
  
  EventUserDetailResponse getEventUserDetail(Integer eventSerial, String mberId);
  
  List<PointPaymentEntity> getEventPointPaymentList(Integer eventSerial);  
  
  String getDrwtMthdCode(EventUserDetailRequest req);
  
  void addFrscParticipation(EventUserDetailRequest req, String mberId);
  
  void addRandParticipation(EventUserDetailRequest req, String mberId);
  
  void addEventRaffle(EventUserDetailRequest req, String mberId);
  
  List<PrizeWinnerResponse> getEntryEventWinningList(Integer eventSn, String mberId);
  
  EventPrizeWinnerResponse getPrizeAt(Integer eventSn, String mberId);

}
