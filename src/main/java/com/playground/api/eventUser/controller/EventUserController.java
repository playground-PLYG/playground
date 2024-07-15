package com.playground.api.eventUser.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.eventUser.model.EventUserListRequest;
import com.playground.api.eventUser.model.EventUserListResponse;
import com.playground.api.eventUser.service.EventUserService;
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
}
