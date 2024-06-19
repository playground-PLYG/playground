package com.playground.api.menu.service;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.member.model.MberSrchRequest;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.model.MenuRequest;
import com.playground.api.menu.model.MenuResponse;
import com.playground.api.menu.repository.MenuRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;


  /**
   * 사이드바 노출메뉴 조회
   * 
   * @param req
   * @return
   */
  @Transactional(readOnly = true)
  public List<MenuResponse> getMenuList(@Valid MberSrchRequest req) {
    List<MenuResponse> upperList = menuRepository.getUpperMenuList(req.getMberId());
    List<MenuResponse> lowerList = menuRepository.getLowerMenuList(req.getMberId());

    return Stream.concat(upperList.stream(), lowerList.stream()).toList();
  }


  /**
   * 메뉴 목록 페이지 조회
   * 
   * @param pageable
   * @param request
   * @return
   */
  @Transactional(readOnly = true)
  public Page<MenuResponse> getMenuPageList(Pageable pageable, MenuRequest request) {

    Page<MenuEntity> menuPageList = menuRepository.getMenuPageList(request.getMenuNm(), request.getMenuUrl(), request.getUseAt(), pageable);

    List<MenuResponse> menuList = menuPageList.getContent().stream()
        .map(entity -> MenuResponse.builder().menuSn(entity.getMenuSn()).menuNm(entity.getMenuNm()).menuUrl(entity.getMenuUrl())
            .menuSortOrdr(entity.getMenuSortOrdr()).upperMenuSn(entity.getUpperMenuSn()).useAt(entity.getUseAt()).registUsrId(entity.getRegistUsrId())
            .registDt(entity.getRegistDt()).updtUsrId(entity.getUpdtUsrId()).updtDt(entity.getUpdtDt()).build())
        .toList();

    return new PageImpl<>(menuList, menuPageList.getPageable(), menuPageList.getTotalElements());
  }


  /**
   * 메뉴 상세 조회
   * 
   * @param upperMenuSn
   * @return
   */
  @Transactional(readOnly = true)
  public MenuResponse getMenuDetail(Integer menuSn) {

    if (menuSn != null) {
      MenuEntity menuEntity = menuRepository.findByMenuSn(menuSn).orElse(MenuEntity.builder().build());

      return MenuResponse.builder().menuSn(menuEntity.getMenuSn()).menuNm(menuEntity.getMenuNm()).menuUrl(menuEntity.getMenuUrl())
          .menuSortOrdr(menuEntity.getMenuSortOrdr()).upperMenuSn(menuEntity.getUpperMenuSn()).useAt(menuEntity.getUseAt())
          .registUsrId(menuEntity.getRegistUsrId()).registDt(menuEntity.getRegistDt()).updtUsrId(menuEntity.getUpdtUsrId())
          .updtDt(menuEntity.getUpdtDt()).build();
    } else {
      return new MenuResponse();
    }
  }


  /**
   * 메뉴 등록
   * 
   * @param request
   * @return
   */
  @Transactional
  public MenuResponse addMenu(MenuRequest request) {

    MenuEntity menuEntity =
        menuRepository.save(MenuEntity.builder().menuNm(request.getMenuNm()).menuUrl(request.getMenuUrl()).menuDepth(request.getMenuDepth())
            .menuSortOrdr(request.getMenuSortOrdr()).upperMenuSn(request.getUpperMenuSn()).useAt(request.getUseAt()).build());

    return MenuResponse.builder().menuSn(menuEntity.getMenuSn()).menuNm(menuEntity.getMenuNm()).menuUrl(menuEntity.getMenuUrl())
        .menuSortOrdr(menuEntity.getMenuSortOrdr()).upperMenuSn(menuEntity.getUpperMenuSn()).useAt(menuEntity.getUseAt())
        .registUsrId(menuEntity.getRegistUsrId()).registDt(menuEntity.getRegistDt()).updtUsrId(menuEntity.getUpdtUsrId())
        .updtDt(menuEntity.getUpdtDt()).build();
  }


  /**
   * 메뉴 수정
   * 
   * @param request
   * @return
   */
  @Transactional
  public MenuResponse modifyMenu(MenuRequest request) {

    MenuEntity menuEntity = menuRepository.save(
        MenuEntity.builder().menuSn(request.getMenuSn()).menuNm(request.getMenuNm()).menuUrl(request.getMenuUrl()).menuDepth(request.getMenuDepth())
            .menuSortOrdr(request.getMenuSortOrdr()).upperMenuSn(request.getUpperMenuSn()).useAt(request.getUseAt()).build());

    return MenuResponse.builder().menuSn(menuEntity.getMenuSn()).menuNm(menuEntity.getMenuNm()).menuUrl(menuEntity.getMenuUrl())
        .menuSortOrdr(menuEntity.getMenuSortOrdr()).upperMenuSn(menuEntity.getUpperMenuSn()).useAt(menuEntity.getUseAt())
        .registUsrId(menuEntity.getRegistUsrId()).registDt(menuEntity.getRegistDt()).updtUsrId(menuEntity.getUpdtUsrId())
        .updtDt(menuEntity.getUpdtDt()).build();
  }


  /**
   * 메뉴 삭제
   * 
   * @param menuSn
   * @return
   */
  @Transactional
  public void removeMenu(List<MenuRequest> request) {

    for (int i = 0; i < request.size(); i++) {

      Integer menuSn = request.get(i).getMenuSn();

      menuRepository.deleteByMenuSn(menuSn).orElse(MenuEntity.builder().build());
    }
  }


  /**
   * 메뉴 사용여부 변경
   * 
   * @param menuSn
   * @return
   */
  @Transactional
  public void modifyUseAtMenu(List<MenuRequest> request) {

    for (int i = 0; i < request.size(); i++) {

      String useAt = request.get(i).getUseAt();

      if ("N".equals(useAt)) {
        useAt = "Y";
        request.get(i).setUseAt("Y");
      } else {
        request.get(i).setUseAt("N");
      }

      menuRepository.save(MenuEntity.builder().menuSn(request.get(i).getMenuSn()).menuNm(request.get(i).getMenuNm())
          .menuUrl(request.get(i).getMenuUrl()).menuDepth(request.get(i).getMenuDepth()).menuSortOrdr(request.get(i).getMenuSortOrdr())
          .upperMenuSn(request.get(i).getUpperMenuSn()).useAt(request.get(i).getUseAt()).build());
    }
  }

}
