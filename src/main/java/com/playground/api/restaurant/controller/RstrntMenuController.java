package com.playground.api.restaurant.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.restaurant.model.RstrntMenuAddRequest;
import com.playground.api.restaurant.model.RstrntMenuDetailRequest;
import com.playground.api.restaurant.model.RstrntMenuListRequest;
import com.playground.api.restaurant.model.RstrntMenuModifyRequest;
import com.playground.api.restaurant.model.RstrntMenuRemoveRequest;
import com.playground.api.restaurant.model.RstrntMenuResponse;
import com.playground.api.restaurant.service.RstrntMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "restaurant menu", description = "식당 메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class RstrntMenuController {
  private final RstrntMenuService rstrntService;

  /**
   * 식당 메뉴 리스트 조회
   */
  @Operation(summary = "식당 메뉴 리스트 조회", description = "식당의 메뉴 목록 조회")
  @PostMapping("public/restaurant/getRstrntMenuList")
  public List<RstrntMenuResponse> getRstrntMenuList(@RequestBody @Valid RstrntMenuListRequest reqData) {
    return rstrntService.getRstrntMenuList(reqData);
  }

  /**
   * 식당 메뉴 단건 조회
   */
  @Operation(summary = "식당 메뉴 단건 조회", description = "식당 메뉴 단건 조회")
  @PostMapping("public/restaurant/getRstrntMenuDetail")
  public RstrntMenuResponse getRstrntMenuDetail(@RequestBody @Valid RstrntMenuDetailRequest reqData) {
    return rstrntService.getRstrntMenuDetail(reqData);
  }

  /**
   * 식당 메뉴 저장
   */
  @Operation(summary = "식당 메뉴 저장", description = "식당 메뉴 저장")
  @PostMapping("public/restaurant/addRstrntMenu")
  public RstrntMenuResponse addRstrntMenu(@RequestBody @Valid RstrntMenuAddRequest reqData) {
    return rstrntService.addRstrntMenu(reqData);
  }

  /**
   * 식당 메뉴 수정
   */
  @Operation(summary = "식당 메뉴 수정", description = "식당 메뉴 수정")
  @PostMapping("public/restaurant/modifyRstrntMenu")
  public RstrntMenuResponse modifyRstrntMenu(@RequestBody @Valid RstrntMenuModifyRequest reqData) {
    return rstrntService.modifyRstrntMenu(reqData);
  }

  /**
   * 식당 메뉴 삭제
   */
  @Operation(summary = "식당 메뉴 삭제", description = "식당 메뉴 삭제")
  @PostMapping("public/restaurant/removeRstrntMenu")
  public void removeRstrntMenu(@RequestBody @Valid RstrntMenuRemoveRequest reqData) {
    rstrntService.removeRstrntMenu(reqData);
  }
}
