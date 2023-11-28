package com.playground.api.menu.entity.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.playground.api.menu.entity.MenuEntity;
import com.playground.api.sample.entity.SampleUserEntity;

@Component
public class MenuSpecification {
  public Specification<MenuEntity> searchCondition(MenuEntity menuEntity) {
    Specification<MenuEntity> spec = (root, query, criteriaBuilder) -> null;

    if (menuEntity != null) {
      String menuNm = menuEntity.getMenuNm();
      String menuUrl = menuEntity.getMenuUrl();
      String useYn = menuEntity.getUseYn();
      
      if (StringUtils.isNotBlank(menuNm)) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("menuNm"), "%" + menuNm + "%"));
      }

      if (StringUtils.isNotBlank(menuUrl)) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("menuUrl"), "%" + menuUrl + "%"));
      }

      if (StringUtils.isNotBlank(useYn)) {
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("useYn"), useYn));
      }
    }

    return spec;
  }
}
