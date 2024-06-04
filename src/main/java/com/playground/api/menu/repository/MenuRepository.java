package com.playground.api.menu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.repository.dsl.MenuRepositoryCustom;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer>, MenuRepositoryCustom {
  /** 메뉴 조회 */
  List<MenuEntity> findByUseAtOrderByMenuSn(String useAt);

  /** 전체 메뉴 목록 조회 */
  List<MenuEntity> findAllByOrderByMenuSn();
}
