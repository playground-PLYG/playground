package com.playground.api.code.entity.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.playground.api.code.entity.CodeEntity;

@Component
public class CodeSpecification {
  public Specification<CodeEntity> searchCondition(CodeEntity codeEntity) {
    Specification<CodeEntity> spec = (root, query, criteriaBuilder) -> null;

    if (codeEntity != null) {
      if (StringUtils.isNotBlank(codeEntity.getCdId())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("cdId"), codeEntity.getCdId() + "%"));
      }
      
     if (StringUtils.isNotBlank(codeEntity.getCdNm())) {
          spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("cdNm"), codeEntity.getCdNm() + "%"));
        }

     if (StringUtils.isNotBlank(codeEntity.getUpCdId())) {
          spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("cdNm"), codeEntity.getUpCdId() + "%"));
          spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("groupCdYn"), "Y"));
        }
     
     if (StringUtils.isNotBlank(codeEntity.getGroupCdYn())) {
         spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("groupCdYn"), codeEntity.getGroupCdYn() + "%"));
       }
      }

    return spec;
  }
  
}
