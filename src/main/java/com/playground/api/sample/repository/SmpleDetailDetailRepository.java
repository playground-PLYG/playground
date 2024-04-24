package com.playground.api.sample.repository;

import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleDetailDetailEntity;
import com.playground.api.sample.entity.SmpleDetailDetailPK;

public interface SmpleDetailDetailRepository extends CrudRepository<SmpleDetailDetailEntity, SmpleDetailDetailPK> {
}
