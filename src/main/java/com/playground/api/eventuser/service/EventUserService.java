package com.playground.api.eventuser.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventuser.model.EventUserDetailRequest;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListRequest;
import com.playground.api.eventuser.model.EventUserListResponse;
import com.playground.api.eventuser.model.PointPaymentResponse;
import com.playground.api.eventuser.repository.EventUserRepository;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.constants.MessageCode;
import com.playground.exception.BizException;
import com.playground.utils.MberUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventUserService {

  private final MberUtil mberUtil;
  private final EventUserRepository eventUserRepository;

  public static final String FIRST_COME = "FRSC";
  public static final String RANDOM = "RAND";

  /** 사용자 이벤트 목록 조회 */
  @Transactional(readOnly = true)
  public List<EventUserListResponse> getEventList(EventUserListRequest req) {

    String mberId = "";
    Optional<MberInfoResponse> mberInfo = mberUtil.getMber();

    if (mberInfo.isPresent()) {
      mberId = mberInfo.get().getMberId();
    }

    List<EventUserListResponse> result = eventUserRepository.getEventList(req.getEventName(), req.getProgrsSttus(), mberId);

    return result;
  }


  /** 사용자 이벤트 상세 조회 */
  @Transactional(readOnly = true)
  public EventUserDetailResponse getEventDetail(Integer EventSn) {

    String mberId = "";
    Optional<MberInfoResponse> mberInfo = mberUtil.getMber();

    if (mberInfo.isPresent()) {
      mberId = mberInfo.get().getMberId();
    }

    // 이벤트 정보, 참여여부
    EventUserDetailResponse EventUserDetailResponse = eventUserRepository.getEventUserDetail(EventSn, mberId);

    // 이벤트 지급 정보
    List<PointPaymentEntity> EventPointPaymentList = eventUserRepository.getEventPointPaymentList(EventSn);

    List<PointPaymentResponse> pointList = EventPointPaymentList.stream()
        .map(entityList -> PointPaymentResponse.builder().pointPaymentUnitValue(entityList.getPointPymntUnitValue())
            .fixingPointPayrCount(entityList.getFixingPointPayrCo()).fixingPointValue(entityList.getFixingPointValue()).build())
        .collect(Collectors.toList());

    EventUserDetailResponse.setPointPayment(pointList);

    return EventUserDetailResponse;
  }

  /** 사용자 이벤트 참여 */
  @Transactional
  public void addEventParticipation(EventUserDetailRequest req) {

    String mberId = "";
    Optional<MberInfoResponse> mberInfo = mberUtil.getMber();

    if (mberInfo.isPresent()) {
      // 로그인 사용자 정보 있는 경우
      mberId = mberInfo.get().getMberId();
    } else {
      // 로그인 사용자 정보가 없거나 로그인 하지 않은 경우
      throw new BizException(MessageCode.ACESS_DENIED_EMAIL);
    }

    // 추첨방식
    String drwtMthdCode = eventUserRepository.getDrwtMthdCode(req);

    if (drwtMthdCode.toUpperCase().equals(FIRST_COME)) { // 순차지급
      eventUserRepository.addFrscParticipation(req, mberId);
    } else if (drwtMthdCode.toUpperCase().equals(RANDOM)) { // 랜덤
      eventUserRepository.addRandParticipation(req, mberId);
    }

  }


  @Transactional
  public void addEventRaffle(EventUserDetailRequest req) {

    String mberId = "";
    Optional<MberInfoResponse> mberInfo = mberUtil.getMber();

    if (mberInfo.isPresent()) {
      // 로그인 사용자 정보 있는 경우
      mberId = mberInfo.get().getMberId();
    } else {
      // 로그인 사용자 정보가 없거나 로그인 하지 않은 경우
      throw new BizException(MessageCode.ACESS_DENIED_EMAIL);
    }
    eventUserRepository.addEventRaffle(req, mberId);
  }

}
