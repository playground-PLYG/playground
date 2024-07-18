package com.playground.api.message.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.service.CodeService;
import com.playground.api.message.component.DiscordComponent;
import com.playground.api.message.model.DiscordRequest;
import com.playground.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

  // @Value("${discord.webhookURL}")
  // private String url;

  private final DiscordComponent discordComponent;

  private final CodeService codeService;

  private final ObjectMapper objectMapper;

  public void sendDiscordWebhook(DiscordRequest req) {

    try {

      CodeSrchResponse code = codeService.getCode(CodeSrchRequest.builder().code(req.getApiNm()).upperCode("API_KEY").build());

      String url = code.getCodeName();

      if ("NOW".equals(req.getVoteTransmissionCode())) {

        discordComponent.sendDiscord(url, req);

      }
      // RestClient 세팅 1차 버전
      /*
       * 받은 dto json 형식으로 변환 ObjectMapper mapper = new ObjectMapper(); String jsonString = mapper.writeValueAsString(req);
       * 
       * RestClient restClient = RestClient.create();
       * 
       * ResponseEntity<Void> response = restClient .post() // post 방식으로 요청 .uri(url) // 어디에 보낼지? .contentType(MediaType.APPLICATION_JSON) // 어떤 타입으로 보낼 것인가 .body(jsonString) // 데이터 내용 .retrieve() .toBodilessEntity(); // 응답을 본문이 없는 엔터티로 반환
       * 
       * log.debug("response :: {}", response);
       */

    } catch (HttpClientErrorException e) {
      log.debug("asdf : {}", e.getResponseBodyAsString());

      String errorText = e.getResponseBodyAsString();

      // message 있을 경우 code, message 넣어주고 없으면 하드코딩
      if (errorText.contains("message")) {
        try {
          @SuppressWarnings("unchecked")
          Map<String, Object> map = objectMapper.readValue(errorText, Map.class);
          throw new BizException(map.get("message").toString());
        } catch (JsonProcessingException e1) {
          throw new BizException("Discord 메시지 전송 중 실패하였습니다.");
        }
      }
      throw new BizException("Discord 메시지 전송 중 실패하였습니다.");
    }

  }
}
