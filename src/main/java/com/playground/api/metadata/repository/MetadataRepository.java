package com.playground.api.metadata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.metadata.entity.MetadataEntity;

public interface MetadataRepository extends JpaRepository<MetadataEntity, String> {
}
