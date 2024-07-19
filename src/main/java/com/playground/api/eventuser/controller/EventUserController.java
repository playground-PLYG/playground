package com.playground.api.eventuser.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.eventuser.model.EventPrizeWinnerResponse;
import com.playground.api.eventuser.model.EventUserDetailRequest;
import com.playground.api.eventuser.model.EventUserDetailResponse;
import com.playground.api.eventuser.model.EventUserListRequest;
import com.playground.api.eventuser.model.EventUserListResponse;
import com.playground.api.eventuser.service.EventUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "eventUser", description = "이벤트유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class EventUserController {

  private final EventUserService eventUserService;

  /**
   * 사용자 이벤트 목록 조회
   */
  @Operation(summary = "사용자 이벤트 목록 조회", description = "사용자 이벤트 목록 조회")
  @PostMapping("/public/eventUser/getEventList")
  public List<EventUserListResponse> getEventList(@RequestBody @Valid EventUserListRequest req) {
    return eventUserService.getEventList(req);
  }

  /**
   * 사용자 이벤트 상세 조회
   */
  @Operation(summary = "사용자 이벤트 상세 조회", description = "사용자 이벤트 상세 조회")
  @GetMapping("/public/eventUser/getEventDetail")
  public EventUserDetailResponse getEventDetail(@RequestParam Integer eventSn) {
    return eventUserService.getEventDetail(eventSn);
  }

  /**
   * 사용자 이벤트 참여
   */
  @Operation(summary = "사용자 이벤트 참여", description = "사용자 이벤트 참여")
  @PostMapping("/api/eventUser/addEventParticipation")
  public void addEventParticipation(@RequestBody EventUserDetailRequest req) {
    eventUserService.addEventParticipation(req);
  }
  
  /**
   * 사용자 이벤트 응모
   */
  @Operation(summary = "사용자 이벤트 응모", description = "사용자 이벤트 응모")
  @PostMapping("/api/eventUser/addEventRaffle")
  public void addEventRaffle(@RequestBody EventUserDetailRequest req) {
    eventUserService.addEventRaffle(req);
  }
  
  /**
   * 응모형 이벤트 당첨자 조회
   */
  @Operation(summary = "응모형 이벤트 당첨자 조회", description = "응모형 이벤트 당첨자 조회")
  @PostMapping("/public/eventUser/getEntryEventWinner")
  public EventPrizeWinnerResponse getEntryEventWinner(@RequestBody Integer eventSn) {
    return eventUserService.getEntryEventWinner(eventSn);
  }

}
