package com.playground.api.menu.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.model.MenuResponse;
import com.playground.api.menu.model.SaveMenuRequest;
import com.playground.api.menu.model.SearchMenuRequest;
import com.playground.api.menu.service.MenuService;
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
   * 
   * @return List<MenuResponse> 메뉴 리스트
   */
  @Operation(summary = "메뉴 조회", description = "메뉴 조회")
  @GetMapping("/public/menu/select")
  public List<MenuResponse> selectMenu() {
    return menuService.selectMenu();
  }

  /**
   * 전체 메뉴 목록 조회
   * 
   * @return List<MenuResponse> 메뉴 리스트
   */
  @Operation(summary = "전체 메뉴 목록 조회", description = "전체 메뉴 목록 조회")
  @PostMapping("/public/menu/select-all")
  public Page<MenuEntity> selectAllMenu(Pageable pageable) {
    return menuService.selectAllMenu(pageable);
  }

  /**
   * 메뉴 저장
   * 
   * @param SaveMenuRequest 메뉴 저장을 위한 request
   * @return MenuResponse 단일 메뉴
   */
  @Operation(summary = "메뉴 저장", description = "메뉴 수정/저장")
  @PostMapping("/public/menu/save")
  public MenuResponse saveMenu(@RequestBody @Valid SaveMenuRequest request) {
    return menuService.saveMenu(request);
  }

  /**
   * 조건별 메뉴 조회
   * 
   * @param SearchMenuRequest 메뉴 조회를 위한 request. menuNm, menuUrl, useYn 세 항목에 대해서만 값이 존재
   * @return List<MenuResponse> 메뉴 리스트
   */
  @Operation(summary = "조건별 메뉴 조회", description = "조건별 메뉴 조회")
  @PostMapping("/public/menu/select-by-condition")
  public List<MenuResponse> selectByCondition(@RequestBody @Valid SearchMenuRequest request) {
    return menuService.selectByCondition(request);
  }
}


