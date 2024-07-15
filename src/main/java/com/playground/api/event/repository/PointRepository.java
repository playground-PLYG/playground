package com.playground.api.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.event.entity.PointEntity;
import com.playground.api.event.entity.PointEntityPK;


public interface PointRepository extends JpaRepository<PointEntity, PointEntityPK> {

}
