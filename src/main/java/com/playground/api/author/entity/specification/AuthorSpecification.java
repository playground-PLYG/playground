package com.playground.api.author.entity.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.playground.api.author.entity.AuthorEntity;

@Component
public class AuthorSpecification {
  public Specification<AuthorEntity> searchCondition(AuthorEntity authorEntity) {
    Specification<AuthorEntity> spec = (root, query, criteriaBuilder) -> null;

    if (authorEntity != null) {
      if (authorEntity.getAuthorId() != null) {
        spec = spec.and(
            (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("authorId").as(String.class), "%" + authorEntity.getAuthorId() + "%"));
      }

      if (StringUtils.isNotBlank(authorEntity.getAuthorNm())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("authorNm"), "%" + authorEntity.getAuthorNm() + "%"));
      }

      if (StringUtils.isNotBlank(authorEntity.getDeleteAt())) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleteAt"), authorEntity.getDeleteAt()));
      }
    }

    return spec;
  }

}
