package com.playground.api.event.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.event.model.EventRequest;
import com.playground.api.event.model.EventResponse;
import com.playground.api.event.model.EventResultResponse;
import com.playground.api.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "event", description = "이벤트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class EventController {
  private final EventService eventService;

  /**
   * 이벤트 생성
   */
  @Operation(summary = "이벤트 생성", description = "이벤트 생성")
  @PostMapping("/api/event/addEvent")
  public void addEvent(@RequestBody EventRequest eventRequest) {
    eventService.addEvent(eventRequest);
  }

  /**
   * 이벤트 페이징 목록조회
   */
  @Operation(summary = "이벤트 목록 조회", description = "이벤트 목록 조회")
  @PostMapping("/public/event/getEventList")
  public Page<EventResponse> getEventList(Pageable pageable, @RequestBody @Valid EventRequest req) {
    return eventService.getEventList(pageable, req);
  }

  /**
   * 이벤트 수정
   */
  @Operation(summary = "이벤트 수정", description = "이벤트 수정")
  @PostMapping("/api/event/modifyEvent")
  public void modifyEvent(@RequestBody EventRequest req) {
    eventService.modifyEvent(req);
  }

  /**
   * 이벤트 종료일자 수정
   */
  @Operation(summary = "이벤트 종료일자 수정", description = "이벤트 종료일자 수정")
  @PostMapping("/api/event/modifyEndEvent")
  public void modifyEndEvent(@RequestBody EventRequest req) {
    eventService.modifyEndEvent(req);
  }

  /**
   * 이벤트 상세 조회
   */
  @Operation(summary = "이벤트 상세 조회", description = "이벤트 상세 조회")
  @PostMapping("/api/event/getEventDetail")
  public EventResponse getEventDetail(@RequestBody EventRequest req) {
    return eventService.getEventDetail(req);
  }

  /**
   * 이벤트 추첨
   */
  @Operation(summary = "이벤트 추첨", description = "이벤트 추첨")
  @PostMapping("/api/event/executeEventRaffle")
  public void executeEventRaffle(@RequestBody EventRequest req) {
    eventService.executeEventRaffle(req);
  }

  /**
   * 이벤트 결과 목록조회
   */
  @Operation(summary = "이벤트 추첨", description = "이벤트 추첨")
  @PostMapping("/api/event/getEventResultList")
  public List<EventResultResponse> getEventResultList(@RequestBody EventRequest req) {
    return eventService.getEventResultList(req.getEventSerial());
  }


}
