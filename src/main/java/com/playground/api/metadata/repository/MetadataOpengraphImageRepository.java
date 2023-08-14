package com.playground.api.metadata.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.metadata.entity.MetadataOpengraphImageEntity;

public interface MetadataOpengraphImageRepository extends JpaRepository<MetadataOpengraphImageEntity, String> {
  List<MetadataOpengraphImageEntity> findByUrl(String url);
}
