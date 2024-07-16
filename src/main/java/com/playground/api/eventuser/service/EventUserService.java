package com.playground.api.eventuser.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventuser.model.EventUserDetailRequest;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListRequest;
import com.playground.api.eventuser.model.EventUserListResponse;
import com.playground.api.eventuser.model.PointPaymentResponse;
import com.playground.api.eventuser.repository.EventUserRepository;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.api.member.service.MberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventUserService {

  private final EventUserRepository eventUserRepository;
  private final MberService mberService;

  /** 사용자 이벤트 목록 조회 */
  public List<EventUserListResponse> getEventList(EventUserListRequest req, String token) {

    String mberId = "";
    try {
      MberInfoResponse member = mberService.getMyInfo(token);
      mberId = member.getMberId();

    } catch (Exception e) {
      // memberId가 없는경우
    }
    List<EventUserListResponse> result = eventUserRepository.getEventList(req.getEventName(), req.getProgrsSttus(), mberId);

    return result;
  }


  /** 사용자 이벤트 상세 조회 */
  public EventUserDetailResponse getEventDetail(EventUserDetailRequest req, String token) {

    String mberId = "";
    try {
      MberInfoResponse member = mberService.getMyInfo(token);
      mberId = member.getMberId();

    } catch (Exception e) {
      // memberId가 없는경우
    }

    // 이벤트 정보, 참여여부
    EventUserDetailResponse EventUserDetailResponse = eventUserRepository.getEventUserDetail(req.getEventSerial(), mberId);

    // 이벤트 지급 정보
    List<PointPaymentEntity> EventPointPaymentList = eventUserRepository.getEventPointPaymentList(req.getEventSerial());

    List<PointPaymentResponse> pointList = EventPointPaymentList.stream()
        .map(entityList -> PointPaymentResponse.builder().pointPaymentUnitValue(entityList.getPointPymntUnitValue())
            .fixingPointPayrCount(entityList.getFixingPointPayrCo()).fixingPointValue(entityList.getFixingPointValue()).build())
        .collect(Collectors.toList());

    EventUserDetailResponse.setPointPayment(pointList);

    return EventUserDetailResponse;
  }
  
}
