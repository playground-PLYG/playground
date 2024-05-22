package com.playground.api.restaurant.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.playground.api.restaurant.entity.RstrntMenuPK;
import com.playground.api.restaurant.repository.dsl.RstrntMenuRepositoryCustom;

@Repository
public interface RstrntMenuRepository extends JpaRepository<RstrntMenuEntity, RstrntMenuPK>, RstrntMenuRepositoryCustom {
  Optional<RstrntMenuEntity> findById(RstrntMenuPK id);

  void deleteByRstrntSn(Integer rstrntSn);
}
