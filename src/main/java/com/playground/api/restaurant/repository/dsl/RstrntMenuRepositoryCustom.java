package com.playground.api.restaurant.repository.dsl;

import java.util.List;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.playground.api.restaurant.model.RstrntMenuResponse;

public interface RstrntMenuRepositoryCustom {
  List<RstrntMenuResponse> findAll(RstrntMenuEntity entity);
}
