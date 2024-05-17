package com.playground.api.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.embedding.Embedding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.ai.service.EmbeddingService;
import com.playground.api.hashtag.entity.HashtagEntity;
import com.playground.api.hashtag.model.HashtagResponse;
import com.playground.api.hashtag.repository.HashtagRepository;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.playground.api.restaurant.entity.RstrntMenuHashtagMapngEntity;
import com.playground.api.restaurant.entity.RstrntMenuHashtagMapngEntity.RstrntMenuHashtagMapngEntityBuilder;
import com.playground.api.restaurant.entity.RstrntMenuHashtagMapngPK;
import com.playground.api.restaurant.entity.RstrntMenuPK;
import com.playground.api.restaurant.model.RstrntMenuDetailRequest;
import com.playground.api.restaurant.model.RstrntMenuHashtagAddRequest;
import com.playground.api.restaurant.model.RstrntMenuHashtagSrchRequest;
import com.playground.api.restaurant.repository.RstrntMenuHashtagMapngRepository;
import com.playground.api.restaurant.repository.RstrntMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RstrntMenuHashtagService {
  private final EmbeddingService embeddingService;
  private final RstrntMenuRepository rstrntMenuRepository;
  private final HashtagRepository hashtagRepository;
  private final RstrntMenuHashtagMapngRepository rstrntMenuHashtagMapngRepository;

  @Transactional(readOnly = true)
  public List<HashtagResponse> getRstrntMenuHashtagList(RstrntMenuDetailRequest reqData) {
    List<HashtagEntity> resList = hashtagRepository.findRstrntMenuHashtag(
        RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build());

    return resList.stream().map(entity -> HashtagResponse.builder().hashtagNo(entity.getHashtagSn()).hashtagName(entity.getHashtagNm()).build())
        .toList();
  }

  @Transactional(readOnly = true)
  public List<HashtagResponse> getMenuHashtagSrchList(RstrntMenuHashtagSrchRequest reqData) {
    List<HashtagEntity> resList;
    String searchKeyword = reqData.getHashtagName();

    if (StringUtils.isBlank(searchKeyword)) {
      resList = hashtagRepository.findRstrntMenuRecommend(reqData.getRestaurantSerialNo(), reqData.getRestaurantMenuSerialNo());
    } else {
      resList = hashtagRepository.findByHashtagNmContains(reqData.getRestaurantSerialNo(), reqData.getRestaurantMenuSerialNo(), searchKeyword);
    }

    return resList.stream().map(entity -> HashtagResponse.builder().hashtagNo(entity.getHashtagSn()).hashtagName(entity.getHashtagNm()).build())
        .toList();
  }

  @Transactional
  public List<HashtagResponse> addMenuHashtag(RstrntMenuHashtagAddRequest reqData) {
    RstrntMenuHashtagMapngEntityBuilder rstrntMenuHashtagMapngEntityBuilder =
        RstrntMenuHashtagMapngEntity.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo());
    HashtagEntity hashtagEntity = null;

    if (reqData.getHashtagSerialNo() != null) {
      hashtagEntity = hashtagRepository.findById(reqData.getHashtagSerialNo()).orElse(null);
    }

    if (hashtagEntity == null) {
      hashtagEntity = hashtagRepository.findByHashtagNm(reqData.getHashtagName());

      if (hashtagEntity == null) {
        hashtagEntity = hashtagRepository.save(HashtagEntity.builder().hashtagNm(reqData.getHashtagName()).build());
      }
    }

    RstrntMenuHashtagMapngEntity rstrntMenuHashtagMapngEntity =
        rstrntMenuHashtagMapngRepository.findById(RstrntMenuHashtagMapngPK.builder().rstrntSn(reqData.getRestaurantSerialNo())
            .rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).hashtagSn(hashtagEntity.getHashtagSn()).build()).orElse(null);

    if (rstrntMenuHashtagMapngEntity == null) {
      rstrntMenuHashtagMapngRepository.save(rstrntMenuHashtagMapngEntityBuilder.hashtagSn(hashtagEntity.getHashtagSn()).build());
    } else {
      List<HashtagEntity> resList = hashtagRepository.findRstrntMenuHashtag(
          RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build());

      return resList.stream().map(entity -> HashtagResponse.builder().hashtagNo(entity.getHashtagSn()).hashtagName(entity.getHashtagNm()).build())
          .toList();
    }

    return excuteMenuEmbedding(RstrntMenuDetailRequest.builder().restaurantSerialNo(reqData.getRestaurantSerialNo())
        .restaurantMenuSerialNo(reqData.getRestaurantMenuSerialNo()).build());
  }

  private List<HashtagResponse> excuteMenuEmbedding(RstrntMenuDetailRequest reqData) {
    // 메뉴명 조회
    RstrntMenuEntity rstrntMenuEntity = rstrntMenuRepository
        .findById(RstrntMenuPK.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build())
        .orElse(null);

    if (rstrntMenuEntity != null) {
      // 태그목록조회
      List<HashtagEntity> resList = hashtagRepository.findRstrntMenuHashtag(
          RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build());
      // 임베딩
      String embeddingMessage =
          rstrntMenuEntity.getRstrntMenuNm() + ", " + resList.stream().map(HashtagEntity::getHashtagNm).collect(Collectors.joining(", "));

      Embedding embedding = embeddingService.getEmbedding(embeddingMessage);

      // 임베딩값 업데이트
      rstrntMenuEntity.changeEmbedding(embedding.getOutput());

      return resList.stream().map(entity -> HashtagResponse.builder().hashtagNo(entity.getHashtagSn()).hashtagName(entity.getHashtagNm()).build())
          .toList();
    } else {
      return new ArrayList<>();
    }
  }
}
