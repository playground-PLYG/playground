package com.playground.api.metadata.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.metadata.entity.MetadataEntity;
import com.playground.api.metadata.repository.MetadataRepository;
import com.playground.constants.CacheType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetadataService {
  private final MetadataRepository metadataRepository;

  @Cacheable(cacheManager = CacheType.TEN_MINUTES, cacheNames = "metadata", key = "#url", unless = "#result == null")
  @Transactional(readOnly = true)
  public MetadataEntity getMetadata(String url) {
    return metadataRepository.findById(url).orElse(null);
  }
}
