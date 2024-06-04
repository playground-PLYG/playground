package com.playground.api.assets.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.assets.model.AssetsRequest;
import com.playground.api.assets.model.AssetsResponse;
import com.playground.api.assets.service.AssetsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "assets", description = " 자산관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class AssetsController {

  private final AssetsService assetesService;
  
  /**
   * 자산관리 목록 조회
   */
  @Operation(summary = "자산관리 목록 조회", description = "자산관리 목록 조회")
  @PostMapping("/public/assets/getAssetsList")
  public Page<AssetsResponse> getAssetsList(Pageable pageable) {
    return assetesService.getAssetsList(pageable);
  }
  
  /**
   * 자산관리 상세 조회
   */
  @Operation(summary = "자산관리 상세 조회", description = "자산관리 상세 조회")
  @PostMapping("/public/assets/getAssetsDetail")
  public AssetsResponse getAssetsDetail(@RequestBody @Valid AssetsRequest req) {
    return assetesService.getAssetsDetail(req);
  }
  
  /**
   * 자산 등록
   */
  @Operation(summary = "자산 등록", description = "자산 등록")
  @PostMapping("/public/assets/addAssets")
  public AssetsResponse addAssets(@RequestBody @Valid AssetsRequest req) {
    return assetesService.addAssets(req);
  }
  
  /**
   * 자산 수정
   */
  @Operation(summary = "자산 수정", description = "자산 수정")
  @PostMapping("/public/assets/modifyAssets")
  public AssetsResponse modifyAssets(@RequestBody @Valid AssetsRequest req) {
    return assetesService.modifyAssets(req);
  }
  
  /**
   * 자산 삭제
   */
  @Operation(summary = "자산 삭제", description = "자산 삭제")
  @PostMapping("/public/assets/removeAssets")
  public void removeAssets(@RequestBody @Valid AssetsRequest req) {
    assetesService.removeAssets(req);
  }
}
