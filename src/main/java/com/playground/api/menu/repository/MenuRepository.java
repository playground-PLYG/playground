package com.playground.api.menu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.playground.api.menu.entity.MenuEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String>, JpaSpecificationExecutor<MenuEntity> {
  /** 메뉴 조회 */
  List<MenuEntity> findByUseAtOrderByMenuSn(String useAt);
  /** 전체 메뉴 목록 조회 */
  List<MenuEntity> findAllByOrderByMenuSn();
}
