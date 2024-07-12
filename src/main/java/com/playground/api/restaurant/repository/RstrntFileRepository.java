package com.playground.api.restaurant.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.playground.api.restaurant.entity.RstrntFileEntity;
import com.playground.api.restaurant.model.RstrntFileResponse;

@Repository
public interface RstrntFileRepository extends JpaRepository<RstrntFileEntity, Integer> {

  List<RstrntFileResponse> findAllByRstrntSn(Integer rstrntSn);

}
