package com.playground.api.code.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.playground.api.code.entity.CodeEntity;;

public interface CodeRepository extends CrudRepository<CodeEntity, String>, JpaSpecificationExecutor<CodeEntity> {

	Page<CodeEntity> findAll(Specification<CodeEntity> searchCondition, Pageable pageable);
	
	
	
}
