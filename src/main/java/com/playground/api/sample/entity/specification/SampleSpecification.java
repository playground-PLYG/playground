package com.playground.api.sample.entity.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.playground.api.sample.entity.SampleUserEntity;

@Component
public class SampleSpecification {
  public Specification<SampleUserEntity> searchCondition(SampleUserEntity sampleEntity) {
    Specification<SampleUserEntity> spec = (root, query, criteriaBuilder) -> null;

    if (sampleEntity != null) {
      if (StringUtils.isNotBlank(sampleEntity.getMberId())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mberId"), sampleEntity.getMberId() + "%"));
      }

      if (StringUtils.isNotBlank(sampleEntity.getMberNm())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mberNm"), sampleEntity.getMberNm() + "%"));
      }

      if (StringUtils.isNotBlank(sampleEntity.getMberEmailAdres())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mberEmailAdres"), sampleEntity.getMberEmailAdres() + "%"));
      }
    }

    return spec;
  }
}
