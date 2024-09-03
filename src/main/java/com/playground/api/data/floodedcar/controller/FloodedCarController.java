package com.playground.api.data.floodedcar.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.data.floodedcar.model.FloodedCarRequest;
import com.playground.api.data.floodedcar.model.FloodedCarResponse;
import com.playground.api.data.floodedcar.service.FloodedCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "flooded car", description = "금융위원회_침수차량진위확인 open API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class FloodedCarController {
  private final FloodedCarService floodedCarService;

  /**
   * 침수차량목록 조회
   */
  @Operation(summary = "침수차량목록 조회", description = "금융위원회 침수차량진위확인 조회")
  @PostMapping("/public/floodedCar/getList")
  public FloodedCarResponse getFloodedCarList(@RequestBody @Valid FloodedCarRequest reqData) {
    return floodedCarService.getFloodedCarList(reqData);
  }
}


