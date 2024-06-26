package com.playground.api.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.embedding.Embedding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.ai.service.EmbeddingService;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.playground.api.restaurant.entity.RstrntMenuEntity.RstrntMenuEntityBuilder;
import com.playground.api.restaurant.entity.RstrntMenuPK;
import com.playground.api.restaurant.model.RstrntMenuAddRequest;
import com.playground.api.restaurant.model.RstrntMenuDetailRequest;
import com.playground.api.restaurant.model.RstrntMenuListRequest;
import com.playground.api.restaurant.model.RstrntMenuModifyRequest;
import com.playground.api.restaurant.model.RstrntMenuRemoveRequest;
import com.playground.api.restaurant.model.RstrntMenuResponse;
import com.playground.api.restaurant.repository.RstrntMenuHashtagMapngRepository;
import com.playground.api.restaurant.repository.RstrntMenuRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RstrntMenuService {
  private final EmbeddingService embeddingService;

  private final RstrntMenuRepository rstrntMenuRepository;
  private final RstrntMenuHashtagMapngRepository rstrntMenuHashtagMapngRepository;

  @Transactional(readOnly = true)
  public List<RstrntMenuResponse> getRstrntMenuList(RstrntMenuListRequest reqData) {
    RstrntMenuEntityBuilder rstrntMenuEntityBuilder = RstrntMenuEntity.builder();

    rstrntMenuEntityBuilder.rstrntSn(reqData.getRestaurantSerialNo());

    if (StringUtils.isNoneBlank(reqData.getMenuName())) {
      rstrntMenuEntityBuilder.rstrntMenuNm(reqData.getMenuName());
    }

    if (reqData.getMenuPrice() != null) {
      rstrntMenuEntityBuilder.rstrntMenuPc(reqData.getMenuPrice());
    }

    List<RstrntMenuResponse> rstrntMenuResponseList = rstrntMenuRepository.findAll(rstrntMenuEntityBuilder.build());

    if (CollectionUtils.isNotEmpty(rstrntMenuResponseList)) {
      return rstrntMenuResponseList;
    } else {
      return new ArrayList<>();
    }
  }

  @Transactional(readOnly = true)
  public RstrntMenuResponse getRstrntMenuDetail(RstrntMenuDetailRequest reqData) {
    RstrntMenuEntity rstrntMenuEntity = rstrntMenuRepository
        .findById(RstrntMenuPK.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build())
        .orElse(null);

    if (rstrntMenuEntity != null) {
      return RstrntMenuResponse.builder().restaurantSerialNo(rstrntMenuEntity.getRstrntSn())
          .restaurantMenuSerialNo(rstrntMenuEntity.getRstrntMenuSn()).menuName(rstrntMenuEntity.getRstrntMenuNm())
          .menuPrice(rstrntMenuEntity.getRstrntMenuPc()).build();
    } else {
      return null;
    }
  }

  @Transactional
  public RstrntMenuResponse addRstrntMenu(RstrntMenuAddRequest reqData) {
    Embedding embedding = embeddingService.getEmbedding(reqData.getMenuName());

    RstrntMenuEntity rstrntMenuEntity = rstrntMenuRepository.save(RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo())
        .rstrntMenuNm(reqData.getMenuName()).rstrntMenuPc(reqData.getMenuPrice()).embedding(embedding.getOutput()).build());

    return RstrntMenuResponse.builder().restaurantSerialNo(rstrntMenuEntity.getRstrntSn()).restaurantMenuSerialNo(rstrntMenuEntity.getRstrntMenuSn())
        .menuName(rstrntMenuEntity.getRstrntMenuNm()).menuPrice(rstrntMenuEntity.getRstrntMenuPc()).build();
  }

  @Transactional
  public RstrntMenuResponse modifyRstrntMenu(RstrntMenuModifyRequest reqData) {
    RstrntMenuEntity rstrntMenuEntityOld = rstrntMenuRepository
        .findById(RstrntMenuPK.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build())
        .orElseGet(RstrntMenuEntity::new);

    RstrntMenuEntityBuilder rstrntMenuEntityBuilder =
        RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo())
            .rstrntMenuNm(reqData.getMenuName()).rstrntMenuPc(reqData.getMenuPrice()).embedding(rstrntMenuEntityOld.getEmbedding());

    if (!StringUtils.equals(rstrntMenuEntityOld.getRstrntMenuNm(), reqData.getMenuName())
        || CollectionUtils.isEmpty(rstrntMenuEntityOld.getEmbedding())) {
      Embedding embedding = embeddingService.getEmbedding(reqData.getMenuName());

      rstrntMenuEntityBuilder.embedding(embedding.getOutput());
    }

    RstrntMenuEntity rstrntMenuEntity = rstrntMenuRepository.save(rstrntMenuEntityBuilder.build());

    return RstrntMenuResponse.builder().restaurantSerialNo(rstrntMenuEntity.getRstrntSn()).restaurantMenuSerialNo(rstrntMenuEntity.getRstrntMenuSn())
        .menuName(rstrntMenuEntity.getRstrntMenuNm()).menuPrice(rstrntMenuEntity.getRstrntMenuPc()).build();
  }

  @Transactional
  public void removeRstrntMenu(RstrntMenuRemoveRequest reqData) {
    rstrntMenuRepository
        .deleteById(RstrntMenuPK.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build());

    rstrntMenuHashtagMapngRepository.deleteByRstrntSnAndRstrntMenuSn(reqData.getRestaurantSerialNo(), reqData.getRestaurantMenuSerialNo());
  }
}
