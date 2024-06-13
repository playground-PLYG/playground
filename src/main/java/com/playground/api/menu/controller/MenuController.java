package com.playground.api.menu.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.playground.api.member.model.MberSrchRequest;
import com.playground.api.menu.model.MenuRequest;
import com.playground.api.menu.model.MenuResponse;
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
   * 사이드바 노출메뉴 조회
   * 
   * @return List<MenuResponse> 메뉴 리스트
   */
  @Operation(summary = "메뉴 조회", description = "메뉴 조회")
  @PostMapping({"/public/menu/select", "/public/menu/getMenuList"})
  public List<MenuResponse> getMenuList(@RequestBody @Valid MberSrchRequest req) {
    return menuService.getMenuList(req);
  }


  /**
   * 메뉴 목록 페이지 조회
   * 
   * @param pageable
   * @param request
   * @return List<MenuResponse> 메뉴 리스트
   */
  @Operation(summary = "메뉴 목록 페이지 조회", description = "메뉴 목록 페이지 조회")
  @PostMapping("/public/menu/getMenuPageList")
  public Page<MenuResponse> getMenuPageList(Pageable pageable, @RequestBody @Valid MenuRequest request) {

    return menuService.getMenuPageList(pageable, request);
  }


  /**
   * 메뉴 상세 조회
   * 
   * @param upperMenuSn
   * @return
   */
  @Operation(summary = "메뉴 상세 조회", description = "메뉴 상세 조회")
  @GetMapping("/public/menu/getMenuDetail")
  public MenuResponse getMenuDetail(@RequestParam("menuSn") String menuSn) {

    return menuService.getMenuDetail(Integer.parseInt(menuSn));
  }


  /**
   * 메뉴 저장
   * 
   * @param request
   * @return
   */
  @Operation(summary = "메뉴 저장", description = "메뉴 저장")
  @PostMapping("/public/menu/addMenu")
  public MenuResponse addMenu(@RequestBody @Valid MenuRequest request) {

    return menuService.addMenu(request);
  }


  /**
   * 메뉴 수정
   * 
   * @param request
   * @return
   */
  @Operation(summary = "메뉴 수정", description = "메뉴 수정")
  @PostMapping("/public/menu/modifyMenu")
  public MenuResponse modifyMenu(@RequestBody @Valid MenuRequest request) {

    return menuService.modifyMenu(request);
  }


  /**
   * 메뉴 삭제
   * 
   * @param menuSn
   * @return
   */
  @Operation(summary = "메뉴 삭제", description = "메뉴 삭제")
  @PostMapping("/public/menu/removeMenu")
  public void removeMenu(@RequestBody @Valid List<MenuRequest> request) {

    menuService.removeMenu(request);
  }

}
