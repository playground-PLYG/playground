package com.playground.api.sample.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleDetailDetailEntity;

public interface SmpleDetailDetailRepository extends CrudRepository<SmpleDetailDetailEntity, String> {
  List<SmpleDetailDetailEntity> findAll();
}
