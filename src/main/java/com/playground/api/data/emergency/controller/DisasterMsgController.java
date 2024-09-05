package com.playground.api.data.emergency.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.data.emergency.model.DisasterMsgRequest;
import com.playground.api.data.emergency.model.DisasterMsgResponse;
import com.playground.api.data.emergency.service.DisasterMsgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Disaster Msg", description = "행안부_긴급재난문자 open API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class DisasterMsgController {
  private final DisasterMsgService disasterMsgService;

  /**
   * 긴급재난문자목록 조회
   */
  @Operation(summary = "긴급재난문자 조회", description = "행정안전부 긴급재난문자 조회")
  @PostMapping("/public/emergencyDisasterMsg/getList")
  public DisasterMsgResponse getDisasterMsgList(@RequestBody @Valid DisasterMsgRequest reqData) {
    return disasterMsgService.getDisasterMsgList(reqData);
  }
}


