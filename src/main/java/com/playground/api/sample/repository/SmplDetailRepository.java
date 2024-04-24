package com.playground.api.sample.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleDetailEntity;

public interface SmplDetailRepository extends CrudRepository<SmpleDetailEntity, String> {
  List<SmpleDetailEntity> findAll();
}
