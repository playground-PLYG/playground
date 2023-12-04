package com.playground.api.code.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.playground.api.code.entity.CodeEntity;;

public interface CodeRepository extends CrudRepository<CodeEntity, String>, JpaSpecificationExecutor<CodeEntity> {

	List<CodeEntity> findAll(Specification<CodeEntity> searchCondition);
	
	List<CodeEntity> findAll();
	
	
	
}
