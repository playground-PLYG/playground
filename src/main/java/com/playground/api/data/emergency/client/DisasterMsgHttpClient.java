package com.playground.api.data.emergency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import com.playground.api.data.emergency.model.DisasterMsgHttpClientResponse;

@Component
@HttpExchange("https://www.safetydata.go.kr/V2/api")
public interface DisasterMsgHttpClient {
  @GetExchange("/DSSP-IF-00247")
  DisasterMsgHttpClientResponse getList(@RequestParam String serviceKey, @RequestParam(required = false) String crtDt,
      @RequestParam(required = false) String rgnNm, @RequestParam int numOfRows, @RequestParam int pageNo);
}
