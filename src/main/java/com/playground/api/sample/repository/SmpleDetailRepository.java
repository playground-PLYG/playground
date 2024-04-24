package com.playground.api.sample.repository;

import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleDetailEntity;
import com.playground.api.sample.entity.SmpleDetailPK;

public interface SmpleDetailRepository extends CrudRepository<SmpleDetailEntity, SmpleDetailPK> {
}
