package com.playground.api.metadata.service;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.metadata.entity.MetadataEntity;
import com.playground.api.metadata.entity.MetadataKeywordEntity;
import com.playground.api.metadata.entity.MetadataOpengraphImageEntity;
import com.playground.api.metadata.model.MetadataResponse;
import com.playground.api.metadata.repository.MetadataKeywordRepository;
import com.playground.api.metadata.repository.MetadataOpengraphImageRepository;
import com.playground.api.metadata.repository.MetadataRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetadataService {
  private final MetadataRepository metadataRepository;
  private final MetadataOpengraphImageRepository metadataOpengraphImageRepository;
  private final MetadataKeywordRepository metadataKeywordRepository;
  private final ModelMapper modelMapper;

  // @Cacheable(cacheManager = CacheType.TEN_MINUTES, cacheNames = "metadata", key = "#url", unless = "#result == null")
  @Transactional(readOnly = true)
  public MetadataResponse getMetadata(String url) {
    MetadataEntity metadata = metadataRepository.findById(url).orElse(null);

    if (metadata != null) {
      MetadataResponse result = modelMapper.map(metadata, MetadataResponse.class);

      List<MetadataOpengraphImageEntity> ogImageList = metadataOpengraphImageRepository.findByUrl(url);

      if (CollectionUtils.isNotEmpty(ogImageList)) {
        result.setOgImages(ogImageList.stream().map(row -> row.getImage()).toList());
      }

      List<MetadataKeywordEntity> keywordList = metadataKeywordRepository.findByUrl(url);

      if (CollectionUtils.isNotEmpty(keywordList)) {
        result.setKeywords(keywordList.stream().map(row -> row.getKeyword()).toList());
      }

      return result;
    }

    return null;
  }
}
