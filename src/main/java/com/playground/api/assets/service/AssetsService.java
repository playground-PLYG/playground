package com.playground.api.assets.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.assets.entity.AssetsEntity;
import com.playground.api.assets.model.AssetsRequest;
import com.playground.api.assets.model.AssetsResponse;
import com.playground.api.assets.repository.AssetsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetsService {

  private final AssetsRepository assetsRepository;
  
  @Transactional
  public Page<AssetsResponse> getAssetsList(Pageable pageable) {
    Page<AssetsEntity> res = assetsRepository.findAll(pageable);
    
    List<AssetsResponse> assetsList = 
    res.getContent().stream().map(entity -> AssetsResponse.builder()
        .assetsNo(entity.getAssetsSn())
        .assetsCategory(entity.getAssetsKndCode())
        .assetsName(entity.getAssetsNm())
        .assetsDescription(entity.getAssetsDc())
        .assetsPrice(entity.getAssetsAmount())
        .useYn(entity.getUseAt())
        .build())
    .toList();
    
    
    return new PageImpl<>(assetsList, res.getPageable(), res.getTotalElements());
  }
  
  @Transactional
  public AssetsResponse getAssetsDetail(AssetsRequest req) {
    AssetsEntity res = assetsRepository.findByAssetsSn(req.getAssetsNo());
    
    return AssetsResponse.builder()
        .assetsNo(res.getAssetsSn())
        .assetsCategory(res.getAssetsKndCode())
        .assetsName(res.getAssetsNm())
        .assetsDescription(res.getAssetsDc())
        .assetsPrice(res.getAssetsAmount())
        .useYn(res.getUseAt())
        .registDate(res.getRegistDt())
        .updtDate(res.getUpdtDt())
        .build(); 
  }
  
  @Transactional
  public AssetsResponse addAssets(AssetsRequest req) {
    AssetsEntity res = assetsRepository.save(AssetsEntity.builder()
        .assetsKndCode(req.getAssetsCategory())
        .assetsNm(req.getAssetsName())
        .assetsDc(req.getAssetsDescription())
        .assetsAmount(req.getAssetsPrice())
        .useAt(req.getUseYn())
      .build());
    
    return AssetsResponse.builder()
            .assetsNo(res.getAssetsSn())
            .assetsCategory(res.getAssetsKndCode())
            .assetsName(res.getAssetsNm())
            .assetsDescription(res.getAssetsDc())
            .assetsPrice(res.getAssetsAmount())
            .useYn(res.getUseAt())
          .build();
  }
  
  @Transactional
  public AssetsResponse modifyAssets(AssetsRequest req) {
    AssetsEntity res = assetsRepository.save(AssetsEntity.builder()
        .assetsSn(req.getAssetsNo())
        .assetsKndCode(req.getAssetsCategory())
        .assetsNm(req.getAssetsName())
        .assetsDc(req.getAssetsDescription())
        .assetsAmount(req.getAssetsPrice())
        .useAt(req.getUseYn())
      .build());
    
    return AssetsResponse.builder()
            .assetsNo(res.getAssetsSn())
            .assetsCategory(res.getAssetsKndCode())
            .assetsName(res.getAssetsNm())
            .assetsDescription(res.getAssetsDc())
            .assetsPrice(res.getAssetsAmount())
            .useYn(res.getUseAt())
          .build();
  }
  
  @Transactional
  public void removeAssets(AssetsRequest req) {
    assetsRepository.delete(AssetsEntity.builder().assetsSn(req.getAssetsNo()).build());
  }
}
