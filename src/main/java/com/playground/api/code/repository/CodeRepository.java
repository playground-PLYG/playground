package com.playground.api.code.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.playground.api.code.entity.CodeEntity;
import com.playground.api.code.repository.dsl.CodeRepositoryCustom;

public interface CodeRepository extends CrudRepository<CodeEntity, Integer>, CodeRepositoryCustom {
  List<CodeEntity> findAll();

  CodeEntity findByCodeNm(String up);

  Optional<CodeEntity> findFirstByCodeIdAndUpperCodeId(String codeId, String upperCodeId);

  List<CodeEntity> findByUpperCodeIdOrderBySortOrdr(String upperCodeId);
}
