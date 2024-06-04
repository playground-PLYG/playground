package com.playground.api.code.repository.dsl;

import java.util.List;
import com.playground.api.code.entity.CodeEntity;

public interface CodeRepositoryCustom {
  List<CodeEntity> findAll(CodeEntity entity);
}
