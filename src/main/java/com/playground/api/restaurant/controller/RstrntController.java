package com.playground.api.restaurant.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.restaurant.model.RstrntAddRequest;
import com.playground.api.restaurant.model.RstrntAddResponse;
import com.playground.api.restaurant.model.RstrntDetailSrchRequest;
import com.playground.api.restaurant.model.RstrntDetailSrchResponse;
import com.playground.api.restaurant.model.RstrntExistCheckRequest;
import com.playground.api.restaurant.model.RstrntModifyImageRequest;
import com.playground.api.restaurant.model.RstrntRemoveRequest;
import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.service.RstrntService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "restaurant", description = "식당 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class RstrntController {

  private final RstrntService rstrntService;

  /**
   * 식당리스트 조회
   */
  @Operation(summary = "식당리스트 조회", description = "식당리스트 조회")
  @PostMapping("public/restaurant/getRstrntList")
  public List<RstrntSrchResponse> getRstrntList(@RequestBody @Valid RstrntSrchRequest req) {
    return rstrntService.getRstrntList(req);
  }

  /**
   * 식당 상세 조회
   */
  @Operation(summary = "식당 상세 조회", description = "식당 상세 조회")
  @PostMapping("public/restaurant/getRstrntDetail")
  public RstrntDetailSrchResponse getRstrntDetail(@RequestBody @Valid RstrntDetailSrchRequest req) {
    return rstrntService.getRstrntDetail(req);
  }

  /**
   * 식당 등록
   */
  @Operation(summary = "식당 등록", description = "식당 등록")
  @PostMapping("/public/restaurant/addRstrnt")
  public RstrntAddResponse addRstrnt(@RequestBody @Valid RstrntAddRequest req) {
    return rstrntService.addRstrnt(req);
  }

  /**
   * 식당 삭제
   */
  @Operation(summary = "식당 삭제", description = "식당 삭제")
  @PostMapping("/public/restaurant/removeRstrnt")
  public void removeRstrnt(@RequestBody @Valid List<RstrntRemoveRequest> req) {
    rstrntService.removeRstrnt(req);
  }


  /**
   * 식당 상세 조회
   */
  @Operation(summary = "식당 중복 여부 조회", description = "식당 중복 여부 조회")
  @PostMapping("public/restaurant/getIsExist")
  public RstrntSrchResponse getIsExist(@RequestBody @Valid RstrntExistCheckRequest req) {
    return rstrntService.getIsExist(req);
  }


  /**
   * 식당 이미지 수정
   */
  @Operation(summary = "식당 이미지 수정", description = "식당 이미지 수정")
  @PostMapping("/public/restaurant/modifyRstrntImage")
  public void modifyRstrntImage(@RequestBody @Valid RstrntModifyImageRequest req) {
    rstrntService.modifyRstrntImage(req);
  }
}
