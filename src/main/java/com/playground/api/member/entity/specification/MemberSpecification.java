package com.playground.api.member.entity.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.playground.api.member.entity.MberEntity;

@Component
public class MemberSpecification {
  public Specification<MberEntity> searchCondition(MberEntity memberEntity) {
    Specification<MberEntity> spec = (root, query, criteriaBuilder) -> null;

    if (memberEntity != null) {
      if (memberEntity.getMberId() != null) {
        spec = spec
            .and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mberId").as(String.class), "%" + memberEntity.getMberId() + "%"));
      }

      if (StringUtils.isNotBlank(memberEntity.getMberNm())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mberNm"), "%" + memberEntity.getMberNm() + "%"));
      }
    }

    return spec;
  }
}
