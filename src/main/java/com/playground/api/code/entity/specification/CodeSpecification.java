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
      if (StringUtils.isNotBlank(codeEntity.getCodeId())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("codeId"), codeEntity.getCodeId() + "%"));
      }
      
     if (StringUtils.isNotBlank(codeEntity.getCodeNm())) {
          spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("codeNm"), codeEntity.getCodeNm() + "%"));
        }

     if (StringUtils.isNotBlank(codeEntity.getUpperCodeId())) {
          spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("codeNm"), codeEntity.getUpperCodeId() + "%"));
          spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("groupCodeAt"), "Y"));
        }
     
     if (StringUtils.isNotBlank(codeEntity.getGroupCodeAt())) {
         spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("groupCodeAt"), codeEntity.getGroupCodeAt() + "%"));
       }
      }

    return spec;
  }
  
}
