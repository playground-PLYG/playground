package com.playground.api.menu.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.entity.specification.MenuSpecification;
import com.playground.api.menu.model.MenuResponse;
import com.playground.api.menu.model.SaveMenuRequest;
import com.playground.api.menu.model.SearchMenuRequest;
import com.playground.api.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final MenuSpecification menuSpecification;
  private final ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<MenuResponse> selectMenu() {
    List<MenuEntity> entityList = menuRepository.findByUseYnOrderByMenuId("Y");
    return entityList.stream().map(item -> modelMapper.map(item, MenuResponse.class)).toList();
  }

  @Transactional(readOnly = true)
  public List<MenuResponse> selectAllMenu() {
    List<MenuEntity> entityList = menuRepository.findAllByOrderByMenuId();
    return entityList.stream().map(item -> modelMapper.map(item, MenuResponse.class)).toList();
  }

  @Transactional
  public MenuResponse saveMenu(SaveMenuRequest request) {
    MenuEntity entity = MenuEntity.builder()
                                  .menuId(request.getMenuId())
                                  .menuNm(request.getMenuNm())
                                  .menuUrl(request.getMenuUrl())
                                  .menuLvl(request.getMenuLvl())
                                  .menuSortOrder(request.getMenuSortOrder())
                                  .parentMenuId(request.getParentMenuId())
                                  .useYn(request.getUseYn())
                                  .build();
    log.debug("entity: {}", entity);
    menuRepository.save(entity);
    log.debug("result entity: {}", entity);
    
    return modelMapper.map(entity, MenuResponse.class);
  }

  @Transactional(readOnly = true)
  public List<MenuResponse> selectByCondition(SearchMenuRequest request) {
    MenuEntity entity = MenuEntity.builder()
                                  .menuNm(request.getMenuNm())
                                  .menuUrl(request.getMenuUrl())
                                  .useYn(request.getUseYn())
                                  .build();
    List<MenuEntity> entityList = menuRepository.findAll(menuSpecification.searchCondition(entity));
    return entityList.stream().map(item -> modelMapper.map(item, MenuResponse.class)).toList();
  }
}
