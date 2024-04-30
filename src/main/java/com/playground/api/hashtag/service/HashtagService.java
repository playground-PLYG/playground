package com.playground.api.hashtag.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.hashtag.entity.HashtagEntity;
import com.playground.api.hashtag.model.HashtagRequest;
import com.playground.api.hashtag.model.HashtagResponse;
import com.playground.api.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagService {

  private final HashtagRepository hashtagRepository;
  
  @Transactional(readOnly = true)
  public List<HashtagResponse> getHashtagList() {
    List<HashtagEntity> resList = hashtagRepository.findAll();
    
    return resList.stream().map(entity -> HashtagResponse.builder()
        .hashtagNo(entity.getHashtagSn())
        .hashtagData(entity.getHashtagNm())
        .build())
        .toList();
  }
  
  @Transactional(readOnly = true)
  public HashtagResponse getHashtagDetail(HashtagRequest req) {
    HashtagEntity resDetail = hashtagRepository.findByHashtagSn(req.getHashtagNo());
    
    return HashtagResponse.builder()
        .hashtagNo(resDetail.getHashtagSn())
        .hashtagData(resDetail.getHashtagNm())
        .build();
  }
  
  @Transactional
  public HashtagResponse addHashtag(HashtagRequest req) {
    HashtagEntity saveEntity = HashtagEntity.builder()
        .hashtagNm(req.getHashtagData())
        .build();
    
    HashtagEntity result = hashtagRepository.save(saveEntity);
    
    return HashtagResponse.builder()
        .hashtagNo(result.getHashtagSn())
        .hashtagData(result.getHashtagNm())
        .build();
  }
  
  @Transactional
  public HashtagResponse modifyHashtag(HashtagRequest req) {
    HashtagEntity updateEntity = HashtagEntity.builder()
        .hashtagSn(req.getHashtagNo())
        .hashtagNm(req.getHashtagData())
        .build();
    
    HashtagEntity result = hashtagRepository.save(updateEntity);
    
    return HashtagResponse.builder()
        .hashtagNo(result.getHashtagSn())
        .hashtagData(result.getHashtagNm())
        .build();
  }
  
  @Transactional
  public void removeHashtag(HashtagRequest req) {
    HashtagEntity deleteEntity = HashtagEntity.builder()
        .hashtagSn(req.getHashtagNo())
        .build();
    
    hashtagRepository.delete(deleteEntity);
  }
  
}
