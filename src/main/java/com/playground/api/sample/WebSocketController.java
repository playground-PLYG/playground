package com.playground.api.sample;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.sample.model.WebSocketDto;
import com.playground.api.sample.service.WebSocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "websocket", description = "websocket 샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground/public/sample/websocket")
public class WebSocketController {
  private final WebSocketService websocketService;

  /**
   * websocket publish 개인
   */
  @Operation(summary = "websocket 개인 메시지 발송", description = "websocket의 연결된 사용자중 특정 사용자(receiverId)에게 메시지 전송")
  @PostMapping("/send-message")
  public void sendMessage(@RequestBody WebSocketDto webSocketDto) {
    websocketService.sendMessage(webSocketDto);
  }

  /**
   * websocket publish 전체
   */
  @Operation(summary = "websocket 전체 메시지 발송", description = "websocket의 구독중인 채널의 전체 사용자에게 메시지 전송")
  @PostMapping("/send-all-message")
  public void sendAllMessage(@RequestBody WebSocketDto webSocketDto) {
    websocketService.sendAllMessage(webSocketDto);
  }
}
