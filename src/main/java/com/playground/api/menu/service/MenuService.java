package com.playground.api.menu.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.entity.specification.MenuSpecification;
import com.playground.api.menu.model.MenuResponse;
import com.playground.api.menu.model.SaveMenuRequest;
import com.playground.api.menu.model.SearchMenuRequest;
import com.playground.api.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final MenuSpecification menuSpecification;
  private final ModelMapper modelMapper;

  /** 메뉴 조회 */
  @Transactional(readOnly = true)
  public List<MenuResponse> selectMenu() {
    List<MenuEntity> entityList = menuRepository.findByUseAtOrderByMenuSn("Y");
    return entityList.stream().map(item -> modelMapper.map(item, MenuResponse.class)).toList();
  }

  /** 전체 메뉴 목록 조회 */
  @Transactional(readOnly = true)
  public List<MenuResponse> selectAllMenu() {
    List<MenuEntity> entityList = menuRepository.findAllByOrderByMenuSn();
    return entityList.stream().map(item -> modelMapper.map(item, MenuResponse.class)).toList();
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
    MenuEntity entity = MenuEntity.builder().menuNm(request.getMenuNm()).menuUrl(request.getMenuUrl()).useAt(request.getUseAt()).build();
    List<MenuEntity> entityList = menuRepository.findAll(menuSpecification.searchCondition(entity), Sort.by("menuId"));
    return entityList.stream().map(item -> modelMapper.map(item, MenuResponse.class)).toList();
  }
}
