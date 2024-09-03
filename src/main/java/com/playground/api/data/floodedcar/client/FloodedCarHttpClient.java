package com.playground.api.data.floodedcar.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import com.playground.api.data.floodedcar.model.FloodedCarResponse;

@Component
@HttpExchange("https://apis.data.go.kr")
public interface FloodedCarHttpClient {
  @GetExchange("/1160100/service/GetASLundService/getAutomobileLundinfo")
  FloodedCarResponse getList(@RequestParam String serviceKey, @RequestParam String resultType, @RequestParam(required = false) String nowVhclNo,
      @RequestParam int numOfRows, @RequestParam int pageNo);
}
