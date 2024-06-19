package com.playground.api.code.repository.dsl;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.playground.api.code.entity.CodeEntity;

public interface CodeRepositoryCustom {
  List<CodeEntity> findAll(CodeEntity entity);

  Page<CodeEntity> getCodePageList(CodeEntity entity, Pageable pageable);
}
