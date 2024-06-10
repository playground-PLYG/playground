package com.playground.api.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.restaurant.entity.RstrntMenuHashtagMapngEntity;
import com.playground.api.restaurant.entity.RstrntMenuHashtagMapngPK;

@Repository
public interface RstrntMenuHashtagMapngRepository extends JpaRepository<RstrntMenuHashtagMapngEntity, RstrntMenuHashtagMapngPK> {
  void deleteByRstrntSnAndRstrntMenuSn(Integer rstrntSn, Integer rstrntMenuSn);

  void deleteAllByRstrntSnIn(List<Integer> rstrntSns);
}
