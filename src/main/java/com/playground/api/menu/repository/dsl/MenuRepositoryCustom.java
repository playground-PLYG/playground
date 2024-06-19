package com.playground.api.menu.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.model.MenuResponse;

public interface MenuRepositoryCustom {

  /**
   * 사이드바 상위메뉴 조회
   * 
   * @param mberId
   * @return
   */
  List<MenuResponse> getUpperMenuList(String mberId);

  /**
   * 사이드바 하위메뉴 조회
   * 
   * @param mberId
   * @return
   */
  List<MenuResponse> getLowerMenuList(String mberId);


  /**
   * 메뉴 목록 페이지 조회
   * 
   * @param menuNm
   * @param menuUrl
   * @param useAt
   * @param pageable
   * @return
   */
  Page<MenuEntity> getMenuPageList(String menuNm, String menuUrl, String useAt, Pageable pageable);

}
