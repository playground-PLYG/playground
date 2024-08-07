package com.playground.api.event.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.playground.api.event.entity.EventEntity;
import com.playground.api.event.entity.EventParticipateEntity;
import com.playground.api.event.entity.PointEntity;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.event.model.EventRequest;
import com.playground.api.event.model.EventResponse;
import com.playground.api.event.model.EventResultExcelResponse;
import com.playground.api.event.model.EventResultResponse;
import com.playground.api.event.model.PointPaymentRequest;
import com.playground.api.event.model.PointPaymentResponse;
import com.playground.api.event.repository.EventParticipateRepository;
import com.playground.api.event.repository.EventRepository;
import com.playground.api.event.repository.PointPaymentRepository;
import com.playground.api.event.repository.PointRepository;
import com.playground.api.sample.repository.ExcelDownRepository;
import com.playground.utils.ExcelDownUtil;
import com.playground.utils.MaskingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
  private final EventRepository eventRepository;

  private final PointPaymentRepository pointPaymentRepository;

  private final EventParticipateRepository eventParticipateRepository;

  private final PointRepository pointRepository;

  private final ExcelDownRepository excelDownRepository;

  /** 이벤트 생성 */
  @Transactional
  public void addEvent(EventRequest eventRequest) {
    EventEntity eventEntity = EventEntity.builder().eventNm(eventRequest.getEventName()).eventBeginDt(eventRequest.getEventBeginDate())
        .eventEndDt(eventRequest.getEventEndDate()).eventThumbFileSn(eventRequest.getEventThumbFileSn()).przwnerCo(eventRequest.getPrzwnerCount())
        .eventSeCodeId(eventRequest.getEventSectionCodeId()).drwtMthdCodeId(eventRequest.getDrwtMethodCodeId())
        .pointPymntMthdCodeId(eventRequest.getPointPymntMethodCodeId()).totPointValue(eventRequest.getTotalPointValue())
        .cntntsCn(eventRequest.getCntntsContents()).expsrAt(eventRequest.getExpsrAt()).build();

    eventEntity = eventRepository.save(eventEntity);


    Integer eventSerial = eventEntity.getEventSn();

    List<PointPaymentRequest> pointPayment = eventRequest.getPointPayment();

    pointPayment.forEach(pntPymnt -> {
      PointPaymentEntity entity = PointPaymentEntity.builder().eventSn(eventSerial).pointPymntUnitValue(pntPymnt.getPointPaymentUnitValue())
          .fixingPointPayrCo(pntPymnt.getFixingPointPayrCount()).fixingPointValue(pntPymnt.getFixingPointValue()).build();
      pointPaymentRepository.save(entity);
    });
  }



  /** 이벤트 목록 조회 */
  @Transactional(readOnly = true)
  public Page<EventResponse> getEventList(Pageable pageable, EventRequest req) {

    Page<EventResponse> eventPageList = eventRepository.getEventList(req, pageable);

    return new PageImpl<EventResponse>(eventPageList.getContent(), eventPageList.getPageable(), eventPageList.getTotalElements());
  }

  @Transactional
  public void modifyEvent(EventRequest req) {
    EventEntity updateEntity = EventEntity.builder().eventSn(req.getEventSerial()).eventNm(req.getEventName()).eventBeginDt(req.getEventBeginDate())
        .eventEndDt(req.getEventEndDate()).eventThumbFileSn(req.getEventThumbFileSn()).przwnerCo(req.getPrzwnerCount())
        .eventSeCodeId(req.getEventSectionCodeId()).drwtMthdCodeId(req.getDrwtMethodCodeId()).pointPymntMthdCodeId(req.getPointPymntMethodCodeId())
        .totPointValue(req.getTotalPointValue()).cntntsCn(req.getCntntsContents()).expsrAt(req.getExpsrAt()).build();
    eventRepository.modifyEvent(updateEntity);
    if (!ObjectUtils.isEmpty(req.getPointPayment())) {

      pointPaymentRepository.deleteByEventSn(req.getEventSerial());

      List<PointPaymentRequest> pointPayment = req.getPointPayment();

      pointPayment.forEach(pntPymnt -> {
        PointPaymentEntity entity =
            PointPaymentEntity.builder().eventSn(req.getEventSerial()).pointPymntUnitValue(pntPymnt.getPointPaymentUnitValue())
                .fixingPointPayrCo(pntPymnt.getFixingPointPayrCount()).fixingPointValue(pntPymnt.getFixingPointValue()).build();
        pointPaymentRepository.save(entity);
      });
    }
  }

  @Transactional
  public void modifyEndEvent(EventRequest req) {
    eventRepository.modifyEndEvent(req.getEventSerial());
  }

  /** 이벤트 목록 조회 */
  @Transactional(readOnly = true)
  public EventResponse getEventDetail(EventRequest req) {
    EventEntity entity = eventRepository.getEventDetail(req.getEventSerial());

    EventResponse eventResponse = EventResponse.builder().eventSerial(entity.getEventSn()).eventName(entity.getEventNm())
        .eventBeginDate(entity.getEventBeginDt()).eventEndDate(entity.getEventEndDt()).eventThumbFileSn(entity.getEventThumbFileSn())
        .przwnerCount(entity.getPrzwnerCo()).eventSectionCodeId(entity.getEventSeCodeId()).drwtMethodCodeId(entity.getDrwtMthdCodeId())
        .expsrAt(entity.getExpsrAt()).pointPymntMethodCodeId(entity.getPointPymntMthdCodeId()).totalPointValue(entity.getTotPointValue())
        .cntntsContents(entity.getCntntsCn()).drwtDate(entity.getDrwtDt()).build();

    List<PointPaymentEntity> pointEntityList = pointPaymentRepository.findByEventSn(req.getEventSerial());

    List<PointPaymentResponse> pointList = pointEntityList.stream()
        .map(entityList -> PointPaymentResponse.builder().eventSerial(entityList.getEventSn()).pointPymntSerial(entityList.getPointPymntSn())
            .pointPaymentUnitValue(entityList.getPointPymntUnitValue()).fixingPointPayrCount(entityList.getFixingPointPayrCo())
            .fixingPointValue(entityList.getFixingPointValue()).build())
        .collect(Collectors.toList());

    eventResponse.setPointPayment(pointList);


    return eventResponse;
  }

  @Transactional
  public void executeEventRaffle(EventRequest req) {

    List<PointPaymentEntity> pointList = pointPaymentRepository.findByEventSn(req.getEventSerial());
    // PointPaymentEntity currentPoint = pointList.remove(0);
    // List<Tuple> participate = eventParticipateRepository.raffleMember(pointList);

    List<EventParticipateEntity> randMberId = eventParticipateRepository.randomMember(req.getEventSerial());
    LocalDateTime expiration = LocalDateTime.now().plusYears(1);

    for (int i = 0; i < pointList.size(); i++) {
      int pointRow = pointList.get(i).getFixingPointPayrCo();

      for (int j = 0; j < pointRow; j++) {

        EventParticipateEntity member = randMberId.remove(0);
        member = EventParticipateEntity.builder().mberId(member.getMberId()).eventSn(member.getEventSn())
            .przwinPointValue(pointList.get(i).getFixingPointValue()).build();
        eventParticipateRepository.prizeUpdate(member);

        PointEntity pointEntity = PointEntity.builder().mberId(member.getMberId()).pointValue(pointList.get(i).getFixingPointValue())
            .validDt(expiration).refrnId(Integer.toString(member.getEventSn())).refrnSeCodeId("EVNT").build();
        pointRepository.save(pointEntity);

      }
    }
    eventRepository.modifyDrwtEvent(req.getEventSerial());
  }

  public EventResultResponse getEventResultList(int eventSn) {
    int provisionPointSum = 0;
    int totalPointSum = 0;

    List<EventResultResponse> resultList = eventRepository.getEventResultList(eventSn);
    List<PointPaymentEntity> pointList = pointPaymentRepository.findByEventSn(eventSn);

    for (PointPaymentEntity point : pointList) {
      totalPointSum += point.getFixingPointPayrCo() * point.getFixingPointValue();
    }

    EventResultResponse response = EventResultResponse.builder().build();

    for (EventResultResponse result : resultList) {
      if ("Y".equals(result.getEventPrzwinAlter())) {
        result.setMemberNm(MaskingUtil.name(result.getMemberNm()));
        response.getWinnerEvent().add(result);
        provisionPointSum += result.getPrzwinPointVal();
      } else if ("N".equals(result.getEventPrzwinAlter())) {
        result.setMemberNm(MaskingUtil.name(result.getMemberNm()));
        response.getLoserEvent().add(result);
      }
    }
    response.setTotalPointValue(totalPointSum);
    response.setProvisionPointValue(provisionPointSum);
    return response;
  }

  public ResponseEntity<byte[]> getEventExcelList(Integer eventSn) {
    // List<ExcelDownEntity> list = excelDownRepository.findAll();
    List<EventResultExcelResponse> list = eventRepository.getEventExcelList(eventSn);
    ExcelDownUtil<EventResultExcelResponse> excel = new ExcelDownUtil<>(list.get(0).getEventName(), "Sheet명", EventResultExcelResponse.class, list);
    return excel.download();
  }

}
