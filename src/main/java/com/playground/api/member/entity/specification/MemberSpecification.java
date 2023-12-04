package com.playground.api.member.entity.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.playground.api.member.entity.PgMemberEntity;


@Component
public class MemberSpecification {
	 public Specification<PgMemberEntity> searchCondition(PgMemberEntity memberEntity) {
		    Specification<PgMemberEntity> spec = (root, query, criteriaBuilder) -> null;
		       

		    if (memberEntity != null) {
		    	
			if (memberEntity.getMbrNo() != null) {
		//	     spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("mbrNo"), memberEntity.getMbrNo()));
			     spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mbrNo").as(String.class), "%"+memberEntity.getMbrNo ()+"%"));
			   }
		      
		     if (StringUtils.isNotBlank(memberEntity.getMbrNm())) {
		          spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mbrNm"), memberEntity.getMbrNm() + "%"));
		        }
		      }

		    return spec;
		  }

}




