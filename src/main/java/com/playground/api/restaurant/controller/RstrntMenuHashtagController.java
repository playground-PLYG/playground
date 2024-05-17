package com.playground.api.restaurant.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.hashtag.model.HashtagResponse;
import com.playground.api.restaurant.model.RstrntMenuDetailRequest;
import com.playground.api.restaurant.model.RstrntMenuHashtagAddRequest;
import com.playground.api.restaurant.model.RstrntMenuHashtagSrchRequest;
import com.playground.api.restaurant.service.RstrntMenuHashtagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "menu hashtag", description = "메뉴의 Hashtag를 관리하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class RstrntMenuHashtagController {
  private final RstrntMenuHashtagService rstrntMenuHashtagService;

  /**
   * 메뉴 해시태그 목록 조회
   */
  @Operation(summary = "메뉴 해시태그 목록 조회", description = "메뉴 해시태그 목록 조회")
  @PostMapping("/public/restaurant/getMenuHashtagList")
  public List<HashtagResponse> getMenuHashtagList(@RequestBody @Valid RstrntMenuDetailRequest reqData) {
    return rstrntMenuHashtagService.getRstrntMenuHashtagList(reqData);
  }

  /**
   * 메뉴 해시태그 자동완성 목록 조회
   */
  @Operation(summary = "메뉴 해시태그 자동완성 목록 조회", description = "메뉴 해시태그 자동완성 목록 조회")
  @PostMapping("/public/restaurant/getMenuHashtagSrchList")
  public List<HashtagResponse> getMenuHashtagSrchList(@RequestBody @Valid RstrntMenuHashtagSrchRequest reqData) {
    return rstrntMenuHashtagService.getMenuHashtagSrchList(reqData);
  }

  /**
   * 메뉴 해시태그 저장
   */
  @Operation(summary = "메뉴 해시태그 저장", description = "메뉴 해시태그 저장")
  @PostMapping("/public/restaurant/addMenuHashtag")
  public List<HashtagResponse> addMenuHashtag(@RequestBody @Valid RstrntMenuHashtagAddRequest reqData) {
    return rstrntMenuHashtagService.addMenuHashtag(reqData);
  }
}
