package com.playground.api.menu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.menu.entity.MenuEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, String> {
  List<MenuEntity> findByUseYnOrderByMenuId(String useYn);
  List<MenuEntity> findAllByOrderByMenuId();
}
