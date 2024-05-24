package com.playground.api.sample.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleEntity;
import com.playground.api.sample.repository.dsl.SmpleRepositoryCustom;

public interface SmpleRepository extends CrudRepository<SmpleEntity, Integer>, SmpleRepositoryCustom {
  Page<SmpleEntity> findAllByOrderBySmpleSn(Pageable pageable);

  Optional<SmpleEntity> findById(Integer smpleSn);
}
