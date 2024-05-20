package com.playground.api.hashtag.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.hashtag.entity.HashtagEntity;
import com.playground.api.hashtag.model.HashtagRequest;
import com.playground.api.hashtag.model.HashtagResponse;
import com.playground.api.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagService {

  private final HashtagRepository hashtagRepository;

  @Transactional(readOnly = true)
  public List<HashtagResponse> getHashtagList() {
    List<HashtagEntity> resList = hashtagRepository.findAll();

    return resList.stream().map(entity -> HashtagResponse.builder().hashtagSerialNo(entity.getHashtagSn()).hashtagName(entity.getHashtagNm()).build())
        .toList();
  }

  @Transactional(readOnly = true)
  public HashtagResponse getHashtagDetail(HashtagRequest req) {
    HashtagEntity resDetail = hashtagRepository.findById(req.getHashtagNo()).orElseGet(HashtagEntity::new);

    return HashtagResponse.builder().hashtagSerialNo(resDetail.getHashtagSn()).hashtagName(resDetail.getHashtagNm()).build();
  }

  @Transactional
  public HashtagResponse addHashtag(HashtagRequest req) {
    HashtagEntity saveEntity = HashtagEntity.builder().hashtagNm(req.getHashtagData()).build();

    HashtagEntity result = hashtagRepository.save(saveEntity);

    return HashtagResponse.builder().hashtagSerialNo(result.getHashtagSn()).hashtagName(result.getHashtagNm()).build();
  }

  @Transactional
  public HashtagResponse modifyHashtag(HashtagRequest req) {
    HashtagEntity updateEntity = HashtagEntity.builder().hashtagSn(req.getHashtagNo()).hashtagNm(req.getHashtagData()).build();

    HashtagEntity result = hashtagRepository.save(updateEntity);

    return HashtagResponse.builder().hashtagSerialNo(result.getHashtagSn()).hashtagName(result.getHashtagNm()).build();
  }

  @Transactional
  public void removeHashtag(HashtagRequest req) {
    HashtagEntity deleteEntity = HashtagEntity.builder().hashtagSn(req.getHashtagNo()).build();

    hashtagRepository.delete(deleteEntity);
  }

}
