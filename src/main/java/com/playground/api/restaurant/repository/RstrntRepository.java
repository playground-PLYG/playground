package com.playground.api.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.playground.api.restaurant.entity.RstrntEntity;

@Repository
public interface RstrntRepository extends JpaRepository<RstrntEntity, String>, JpaSpecificationExecutor<RstrntEntity> {
	
	List<RstrntEntity> findAll(Specification<RstrntEntity> searchCondition);

}
