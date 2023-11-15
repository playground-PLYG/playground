package com.playground.api.menu;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.menu.model.MenuResponse;
import com.playground.api.menu.model.SaveMenuRequest;
import com.playground.api.menu.service.MenuService;
import com.playground.model.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "menu", description = "메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class MenuController {

  private final MenuService menuService;

  /**
   * 메뉴 조회
   */
  @Operation(summary = "메뉴 조회", description = "메뉴 조회")
  @GetMapping("/public/menu/select")
  public ResponseEntity<BaseResponse<List<MenuResponse>>> selectMenu() {
    return ResponseEntity.ok(new BaseResponse<>(menuService.selectMenu()));
  }
  
  /**
   * 전체 메뉴 목록 조회
   */
  @Operation(summary = "전체 메뉴 목록 조회", description = "전체 메뉴 목록 조회")
  @GetMapping("/public/menu/select-all")
  public ResponseEntity<BaseResponse<List<MenuResponse>>> selectAllMenu() {
    return ResponseEntity.ok(new BaseResponse<>(menuService.selectAllMenu()));
  }

  /**
   * 메뉴 저장
   */
  @Operation(summary = "메뉴 저장", description = "메뉴 수정/저장")
  @PostMapping("/public/menu/save")
  public ResponseEntity<BaseResponse<MenuResponse>> saveMenu(@RequestBody @Valid SaveMenuRequest request) {
    return ResponseEntity.ok(new BaseResponse<>(menuService.saveMenu(request)));
  }
}


