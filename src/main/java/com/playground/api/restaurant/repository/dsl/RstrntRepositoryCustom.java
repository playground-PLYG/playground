package com.playground.api.restaurant.repository.dsl;

import java.util.List;
import com.playground.api.restaurant.entity.RstrntEntity;
import com.playground.api.restaurant.model.RstrntSrchResponse;

public interface RstrntRepositoryCustom {
  List<RstrntSrchResponse> findAll(RstrntEntity entity);
}
