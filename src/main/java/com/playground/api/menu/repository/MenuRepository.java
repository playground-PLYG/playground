package com.playground.api.menu.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.menu.repository.dsl.MenuRepositoryCustom;

@Repository
public interface MenuRepository extends CrudRepository<MenuEntity, Integer>, MenuRepositoryCustom {

  /**
   * 메뉴 단건 조회
   * 
   * @param menuSn
   * @return
   */
  Optional<MenuEntity> findByMenuSn(Integer menuSn);


  /**
   * 메뉴 삭제
   * 
   * @param menuSn
   * @return
   */
  Optional<MenuEntity> deleteByMenuSn(Integer menuSn);
}
