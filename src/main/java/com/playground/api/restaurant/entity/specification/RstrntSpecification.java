package com.playground.api.restaurant.entity.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.playground.api.restaurant.entity.RstrntEntity;

@Component
public class RstrntSpecification {
  public Specification<RstrntEntity> searchCondition(RstrntEntity rstrntEntity) {
	    Specification<RstrntEntity> spec = (root, query, criteriaBuilder) -> null;

	    if (rstrntEntity != null) {
	      if (rstrntEntity.getRstrntKndCode() != null) {
	        spec = spec
	            .and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("rstrntKndCode").as(String.class), "%" + rstrntEntity.getRstrntKndCode() + "%"));
	      }

	      if (StringUtils.isNotBlank(rstrntEntity.getRstrntNm())) {
	        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("rstrntNm"), "%" + rstrntEntity.getRstrntNm() + "%"));
	      }
	    }

	    return spec;
	  }
  
}