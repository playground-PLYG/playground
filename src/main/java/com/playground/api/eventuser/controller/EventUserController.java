package com.playground.api.eventuser.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
  @PostMapping("/public/eventUser/getEventDetail")
  public EventUserDetailResponse getEventDetail(@RequestBody @Valid EventUserDetailRequest req) {
    return eventUserService.getEventDetail(req);
  }

  /**
   * 사용자 이벤트 참여
   */
  @Operation(summary = "사용자 이벤트 참여", description = "사용자 이벤트 참여")
  @PostMapping("/public/eventUser/addEventParticipation")
  public EventUserDetailResponse addEventParticipation(@RequestBody @Valid EventUserDetailRequest req) {
    return eventUserService.addEventParticipation(req);
  }

}
