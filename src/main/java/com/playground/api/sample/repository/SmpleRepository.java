package com.playground.api.sample.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleEntity;

public interface SmpleRepository extends CrudRepository<SmpleEntity, Integer> {
  List<SmpleEntity> findAll();

  Optional<SmpleEntity> findById(Integer smpleSn);
}
