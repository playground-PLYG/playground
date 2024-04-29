package com.playground.api.sample.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleEntity;
import com.playground.api.sample.repository.dsl.SmpleRepositoryCustom;

public interface SmpleRepository extends CrudRepository<SmpleEntity, Integer>, SmpleRepositoryCustom {
  List<SmpleEntity> findAll();

  Optional<SmpleEntity> findById(Integer smpleSn);
}
