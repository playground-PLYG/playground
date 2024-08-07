package com.playground.api.metadata.service;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.metadata.entity.MetadataEntity;
import com.playground.api.metadata.entity.MetadataKeywordEntity;
import com.playground.api.metadata.entity.MetadataOpengraphImageEntity;
import com.playground.api.metadata.model.MetadataResponse;
import com.playground.api.metadata.repository.MetadataKeywordRepository;
import com.playground.api.metadata.repository.MetadataOpengraphImageRepository;
import com.playground.api.metadata.repository.MetadataRepository;
import com.playground.constants.CacheType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetadataService {
  private final MetadataRepository metadataRepository;
  private final MetadataOpengraphImageRepository metadataOpengraphImageRepository;
  private final MetadataKeywordRepository metadataKeywordRepository;

  @Cacheable(cacheManager = CacheType.ONE_HOUR, cacheNames = "metadata", key = "#url", unless = "#result == null")
  @Transactional(readOnly = true)
  public MetadataResponse getMetadata(String url) {
    MetadataEntity metadata = metadataRepository.findById(url).orElse(null);

    if (metadata != null) {
      MetadataResponse result = MetadataResponse.builder().url(metadata.getMetdataUrl()).title(metadata.getMetdataSj())
          .description(metadata.getMetdataDc()).category(metadata.getMetdataCategorynCn()).ogTitle(metadata.getPrevewImageSj())
          .ogDescription(metadata.getPrevewImageDc()).ogUrl(metadata.getPrevewImageUrl()).ogSiteName(metadata.getPrevewSiteNm())
          .metdataBassCn(metadata.getMetdataBassCn()).icon(metadata.getMetdataIconNm()).build();

      List<MetadataOpengraphImageEntity> ogImageList = metadataOpengraphImageRepository.findByPrevewImageUrl(url);

      if (CollectionUtils.isNotEmpty(ogImageList)) {
        result.setOgImages(ogImageList.stream().map(row -> row.getPrevewImageCn()).toList());
      }

      List<MetadataKeywordEntity> keywordList = metadataKeywordRepository.findByKwrdUrl(url);

      if (CollectionUtils.isNotEmpty(keywordList)) {
        result.setKeywords(keywordList.stream().map(row -> row.getKwrdCn()).toList());
      }

      return result;
    }

    return null;
  }
}
