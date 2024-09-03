package com.playground.api.data.floodedcar.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.service.CodeService;
import com.playground.api.data.floodedcar.client.FloodedCarHttpClient;
import com.playground.api.data.floodedcar.model.FloodedCarRequest;
import com.playground.api.data.floodedcar.model.FloodedCarResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FloodedCarService {
  private final CodeService codeService;
  private final FloodedCarHttpClient floodedCarHttpClient;

  @Transactional(readOnly = true)
  public FloodedCarResponse getFloodedCarList(FloodedCarRequest reqData) {
    CodeSrchResponse code = codeService.getCode(CodeSrchRequest.builder().upperCode("API_SERVICE_KEY").code("FLOODED_CAR").build());

    return floodedCarHttpClient.getList(code.getCodeValue(), "json", reqData.getNowVhclNo(), reqData.getNumOfRows(), reqData.getPageNo());
  }
}
