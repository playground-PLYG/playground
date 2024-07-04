package com.playground.api.metadata.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.metadata.entity.MetadataKeywordEntity;

public interface MetadataKeywordRepository extends JpaRepository<MetadataKeywordEntity, Integer> {
  List<MetadataKeywordEntity> findByKwrdUrl(String url);
}
