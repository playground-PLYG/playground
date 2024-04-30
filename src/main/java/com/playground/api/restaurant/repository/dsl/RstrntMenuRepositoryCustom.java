package com.playground.api.restaurant.repository.dsl;

import java.util.List;
import com.playground.api.restaurant.entity.RstrntMenuEntity;

public interface RstrntMenuRepositoryCustom {
  List<RstrntMenuEntity> findAllList(RstrntMenuEntity entity);
}
