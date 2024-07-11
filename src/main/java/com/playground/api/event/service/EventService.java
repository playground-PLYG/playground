package com.playground.api.event.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.event.entity.EventEntity;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.event.model.EventRequest;
import com.playground.api.event.model.EventResponse;
import com.playground.api.event.model.PointPaymentRequest;
import com.playground.api.event.repository.EventRepository;
import com.playground.api.event.repository.PointPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;

  private final PointPaymentRepository pointPaymentRepository;

  /** 이벤트 생성 */
  @Transactional
  public void addEvent(EventRequest eventRequest) {
    EventEntity eventEntity = EventEntity.builder().eventNm(eventRequest.getEventName()).eventBeginDt(eventRequest.getEventBeginDate())
        .eventEndDt(eventRequest.getEventEndDate()).eventThumbFileSn(eventRequest.getEventThumbFileSn()).przwnerCo(eventRequest.getPrzwnerCount())
        .eventSeCodeId(eventRequest.getEventSectionCodeId()).drwtMthdCodeId(eventRequest.getDrwtMethodCodeId())
        .pointPymntMthdCodeId(eventRequest.getPointPymntMethodCodeId()).totPointValue(eventRequest.getTotalPointValue())
        .cntntsCn(eventRequest.getCntntsContents()).expsrAt(eventRequest.getExpsrAt()).build();

    eventEntity = eventRepository.save(eventEntity); // 저장 후 반환된 엔터티 객체를 다시 받습니다.


    Integer eventSerial = eventEntity.getEventSn(); // 자동 생성된 eventSn 값을 얻습니다.

    List<PointPaymentRequest> pointPayment = eventRequest.getPointPayment();

    pointPayment.forEach(pntPymnt -> {
      PointPaymentEntity entity = PointPaymentEntity.builder().eventSn(eventSerial)// .pointPymntSn(eventSerial)
          .pointPymntUnitValue(pntPymnt.getPointPaymentUnitValue()).fixingPointPayrCo(pntPymnt.getFixingPointPayrCount())
          .fixingPointValue(pntPymnt.getFixingPointValue()).build();
      pointPaymentRepository.save(entity);
    });
  }



  /** 이벤트 목록 조회 */
  public List<EventResponse> getEventList() {
    return null;
  }


}
