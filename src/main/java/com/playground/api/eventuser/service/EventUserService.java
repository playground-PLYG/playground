package com.playground.api.eventuser.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.event.entity.PointPaymentEntity;
import com.playground.api.eventuser.model.EventParticipationResponse;
import com.playground.api.eventuser.model.EventPrizeWinnerResponse;
import com.playground.api.eventuser.model.EventUserDetailRequest;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListRequest;
import com.playground.api.eventuser.model.EventUserListResponse;
import com.playground.api.eventuser.model.PointPaymentResponse;
import com.playground.api.eventuser.model.PrizeWinnerResponse;
import com.playground.api.eventuser.repository.EventUserRepository;
import com.playground.api.member.model.MberInfoResponse;
import com.playground.constants.MessageCode;
import com.playground.exception.BizException;
import com.playground.utils.MberUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.playground.utils.MaskingUtil;

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

  /** 사용자 이벤트 목록 페이징 조회 */
  public Page<EventUserListResponse> getEventPageList(Pageable pageable, @Valid EventUserListRequest req) {
    String mberId = "";
    Optional<MberInfoResponse> mberInfo = mberUtil.getMber();

    if (mberInfo.isPresent()) {
      mberId = mberInfo.get().getMberId();
    }

    Page<EventUserListResponse> result = eventUserRepository.getEventPageList(req.getEventName(), req.getProgrsSttus(), mberId, pageable);

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
  public EventParticipationResponse addEventParticipation(EventUserDetailRequest req) {

    EventParticipationResponse result = new EventParticipationResponse();

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
      result = eventUserRepository.addFrscParticipation(req, mberId);
    } else if (drwtMthdCode.toUpperCase().equals(RANDOM)) { // 랜덤
      result = eventUserRepository.addRandParticipation(req, mberId);
    }
    return result;

  }


  /** 사용자 이벤트 응모 */
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


  /** 응모형 이벤트 당첨자 조회 */
  @Transactional(readOnly = true)
  public EventPrizeWinnerResponse getEntryEventWinner(Integer eventSn) {

      String mberId = "";
      Optional<MberInfoResponse> mberInfo = mberUtil.getMber();

      EventPrizeWinnerResponse result = new EventPrizeWinnerResponse();
      if (mberInfo.isPresent()) {
          // 로그인 사용자 정보 있는 경우
          mberId = mberInfo.get().getMberId();

          // 당첨여부, 지급값
          result = eventUserRepository.getPrizeAt(eventSn, mberId);
          
          if (result == null) {
              result = new EventPrizeWinnerResponse();
              result.setEventPrizeAt("N");
              result.setPrzwinPointValue(0);
          } else {
              result.setMemberName(MaskingUtil.name(result.getMemberName()));
          }
      } else {
          result.setEventPrizeAt("N");
          result.setPrzwinPointValue(0);
          // 로그인 사용자 정보가 없거나 로그인 하지 않은 경우
      }

      //당첨자리스트
      List<PrizeWinnerResponse> winnerList = eventUserRepository.getEntryEventWinningList(eventSn, mberId);

      if (winnerList == null || winnerList.isEmpty()) {
          winnerList = Collections.emptyList();
      } else {
          for (PrizeWinnerResponse winner : winnerList) {
              winner.setMemberName(MaskingUtil.name(winner.getMemberName()));
          }
      }

      result.setPrizeWinner(winnerList);

      return result;
  }



}
