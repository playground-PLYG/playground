package com.playground.api.event.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.event.model.EventRequest;
import com.playground.api.event.model.EventResponse;
import com.playground.api.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "event", description = "이벤트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class EventController {
  private final EventService eventService;

  /**
   * 이벤트 생성
   *
   */
  @Operation(summary = "이벤트 생성", description = "이벤트 생성")
  @PostMapping("/api/event/addEvent")
  public void addEvent(@RequestBody EventRequest eventRequest) {
    eventService.addEvent(eventRequest);
  }

  /**
   *
   *
   * 이벤트 페이징 목록조회
   */
  @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록 조회")
  @GetMapping("/public/event/getEventList")
  public List<EventResponse> getEventList() {
    return eventService.getEventList();
  }
}
