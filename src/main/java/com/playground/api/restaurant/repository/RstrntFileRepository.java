package com.playground.api.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.restaurant.entity.RstrntFileEntity;
import com.playground.api.restaurant.repository.dsl.RstrntFileRepositoryCustom;

@Repository
public interface RstrntFileRepository extends JpaRepository<RstrntFileEntity, Integer>, RstrntFileRepositoryCustom {

  void deleteAllByRstrntSnIn(List<Integer> rstrntSnList);


}
