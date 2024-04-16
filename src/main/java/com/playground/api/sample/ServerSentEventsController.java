package com.playground.api.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.playground.api.sample.model.SseDto;
import com.playground.api.sample.service.ServerSentEventsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "sse", description = "SSE(Server Sent Events)(EventStream) 샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground/public/sample/sse")
public class ServerSentEventsController {
  private final ServerSentEventsService serverSentEventsService;

  /**
   * sse publish
   */
  @Operation(summary = "sse 메시지 발송", description = "sse의 연결된 사용자(id)에게 메시지 전송")
  @PostMapping("/send-message")
  public void sendMessage(@RequestBody SseDto sseDto) {
    serverSentEventsService.sendMessage(sseDto);
  }

  /**
   * sse 연결
   */
  @Operation(summary = "sse 구독", description = "sse구독")
  @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
  public SseEmitter subscribe(@PathVariable String id,
      @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
    return serverSentEventsService.subscribe(id, lastEventId);
  }
}
