package com.playground.api.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.playground.api.restaurant.entity.RstrntMenuEntity.RstrntMenuEntityBuilder;
import com.playground.api.restaurant.entity.RstrntMenuPK;
import com.playground.api.restaurant.model.RstrntMenuAddRequest;
import com.playground.api.restaurant.model.RstrntMenuDetailSrchRequest;
import com.playground.api.restaurant.model.RstrntMenuModifyRequest;
import com.playground.api.restaurant.model.RstrntMenuRemoveRequest;
import com.playground.api.restaurant.model.RstrntMenuResponse;
import com.playground.api.restaurant.repository.RstrntMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RstrntMenuService {
  private final RstrntMenuRepository rstrntMenuRepository;

  @Transactional(readOnly = true)
  public List<RstrntMenuResponse> getRstrntMenuList(RstrntMenuDetailSrchRequest reqData) {
    RstrntMenuEntityBuilder rstrntMenuEntityBuilder = RstrntMenuEntity.builder();

    rstrntMenuEntityBuilder.rstrntSn(reqData.getRestaurantSerialNo());

    if (StringUtils.isNoneBlank(reqData.getMenuName())) {
      rstrntMenuEntityBuilder.rstrntMenuNm(reqData.getMenuName());
    }

    if (reqData.getMenuPrice() != null) {
      rstrntMenuEntityBuilder.rstrntMenuPc(reqData.getMenuPrice());
    }

    List<RstrntMenuEntity> rstrntMenuEntityList = rstrntMenuRepository.findAllList(rstrntMenuEntityBuilder.build());

    if (CollectionUtils.isNotEmpty(rstrntMenuEntityList)) {
      return rstrntMenuEntityList
          .stream().map(entity -> RstrntMenuResponse.builder().restaurantSerialNo(entity.getRstrntSn())
              .restaurantMenuSerialNo(entity.getRstrntMenuSn()).menuName(entity.getRstrntMenuNm()).menuPrice(entity.getRstrntMenuPc()).build())
          .toList();
    } else {
      return new ArrayList<>();
    }
  }

  @Transactional(readOnly = true)
  public RstrntMenuResponse getRstrntMenuDetail(RstrntMenuDetailSrchRequest reqData) {
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
    RstrntMenuEntity rstrntMenuEntity = rstrntMenuRepository.save(RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo())
        .rstrntMenuNm(reqData.getMenuName()).rstrntMenuPc(reqData.getMenuPrice()).build());

    return RstrntMenuResponse.builder().restaurantSerialNo(rstrntMenuEntity.getRstrntSn()).restaurantMenuSerialNo(rstrntMenuEntity.getRstrntMenuSn())
        .menuName(rstrntMenuEntity.getRstrntMenuNm()).menuPrice(rstrntMenuEntity.getRstrntMenuPc()).build();
  }

  @Transactional
  public RstrntMenuResponse modifyRstrntMenu(RstrntMenuModifyRequest reqData) {
    RstrntMenuEntity rstrntMenuEntity = rstrntMenuRepository.save(RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo())
        .rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).rstrntMenuNm(reqData.getMenuName()).rstrntMenuPc(reqData.getMenuPrice()).build());

    return RstrntMenuResponse.builder().restaurantSerialNo(rstrntMenuEntity.getRstrntSn()).restaurantMenuSerialNo(rstrntMenuEntity.getRstrntMenuSn())
        .menuName(rstrntMenuEntity.getRstrntMenuNm()).menuPrice(rstrntMenuEntity.getRstrntMenuPc()).build();
  }

  @Transactional
  public void removeRstrntMenu(RstrntMenuRemoveRequest reqData) {
    rstrntMenuRepository
        .deleteById(RstrntMenuPK.builder().rstrntSn(reqData.getRestaurantSerialNo()).rstrntMenuSn(reqData.getRestaurantMenuSerialNo()).build());
  }

}
