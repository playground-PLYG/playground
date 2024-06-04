package com.playground.api.menu.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.member.model.MberSrchRequest;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.model.MenuResponse;
import com.playground.api.menu.model.SaveMenuRequest;
import com.playground.api.menu.model.SearchMenuRequest;
import com.playground.api.menu.repository.MenuRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final ModelMapper modelMapper;

  /** 메뉴 조회 */
  @Transactional(readOnly = true)
  public List<MenuResponse> selectMenu(@Valid MberSrchRequest req) {
    return menuRepository.getMenuList(req.getMberId());
  }

  /** 전체 메뉴 목록 조회 */
  @Transactional(readOnly = true)
  public Page<MenuEntity> selectAllMenu(Pageable pageable) {
    return menuRepository.findAll(pageable);
  }

  /** 메뉴 저장 */
  @Transactional
  public MenuResponse saveMenu(SaveMenuRequest request) {
    MenuEntity entity =
        MenuEntity.builder().menuSn(request.getMenuSn()).menuNm(request.getMenuNm()).menuUrl(request.getMenuUrl()).menuDepth(request.getMenuDepth())
            .menuSortOrdr(request.getMenuSortOrdr()).upperMenuSn(request.getUpperMenuSn()).useAt(request.getUseAt()).build();
    menuRepository.save(entity);
    return modelMapper.map(entity, MenuResponse.class);
  }

  /** 조건별 메뉴 조회 */
  @Transactional(readOnly = true)
  public List<MenuResponse> selectByCondition(SearchMenuRequest request) {
    return menuRepository.getSelectByCondition(request.getMenuNm(), request.getMenuUrl(), request.getUseAt());
  }
}
