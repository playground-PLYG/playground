package com.playground.api.sample.repository.redis;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.RedisRepositoryEntity;

public interface RedisRepository extends CrudRepository<RedisRepositoryEntity, String> {
  @Override
  List<RedisRepositoryEntity> findAll();
}
