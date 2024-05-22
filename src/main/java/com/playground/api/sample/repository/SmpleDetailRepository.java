package com.playground.api.sample.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.sample.entity.SmpleDetailEntity;
import com.playground.api.sample.entity.SmpleDetailPK;
import com.playground.api.sample.entity.SmpleEntity;


public interface SmpleDetailRepository extends CrudRepository<SmpleDetailEntity, SmpleDetailPK> {
    List<SmpleDetailEntity> findBySmpleEntity(SmpleEntity smpleEntity);
}
