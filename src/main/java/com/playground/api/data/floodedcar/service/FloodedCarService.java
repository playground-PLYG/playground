package com.playground.api.data.floodedcar.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.service.CodeService;
import com.playground.api.data.floodedcar.client.FloodedCarHttpClient;
import com.playground.api.data.floodedcar.model.FloodedCarRequest;
import com.playground.api.data.floodedcar.model.FloodedCarResponse;
import com.playground.constants.CacheType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FloodedCarService {
  private final CodeService codeService;
  private final FloodedCarHttpClient floodedCarHttpClient;

  @Cacheable(cacheManager = CacheType.ONE_DAY, cacheNames = "floodedCar",
      key = "#reqData.nowVhclNo + '_' + #reqData.numOfRows + '_' + #reqData.pageNo", unless = "#result == null")
  @Transactional(readOnly = true)
  public FloodedCarResponse getFloodedCarList(FloodedCarRequest reqData) {
    CodeSrchResponse code = codeService.getCode(CodeSrchRequest.builder().upperCode("API_SERVICE_KEY").code("FLOODED_CAR").build());

    return floodedCarHttpClient.getList(code.getCodeValue(), "json", reqData.getNowVhclNo(), reqData.getNumOfRows(), reqData.getPageNo());
  }
}
