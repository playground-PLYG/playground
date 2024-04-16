package com.playground.api.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.sample.model.WebSocketDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playground/public/sample/xss")
public class XssFilterTestController {
  @PostMapping("/request-body")
  public WebSocketDto requestBodyTest(@RequestBody WebSocketDto webSocketDto) {
    return webSocketDto;
  }

  @GetMapping("/get")
  public String getTest(@RequestParam String param) {
    return param;
  }
}
