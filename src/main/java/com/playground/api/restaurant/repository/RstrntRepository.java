package com.playground.api.restaurant.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.restaurant.entity.RstrntEntity;
import com.playground.api.restaurant.repository.dsl.RstrntRepositoryCustom;

@Repository
public interface RstrntRepository extends JpaRepository<RstrntEntity, Integer>, RstrntRepositoryCustom {
  void deleteAllByRstrntSnIn(List<Integer> rstrntSns);

  Optional<RstrntEntity> findFirstByRstrntNmOrKakaoMapId(String rstrntNm, String kakaoMapId);
}
