package com.playground.api.sample.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleEntity;

public interface SmpleRepository extends CrudRepository<SmpleEntity, String> {
  List<SmpleEntity> findAll();
}
