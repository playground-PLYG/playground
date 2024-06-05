package com.playground.api.restaurant.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.restaurant.entity.RstrntEntity;
import com.playground.api.restaurant.model.RstrntDetailSrchRequest;
import com.playground.api.restaurant.model.RstrntDetailSrchResponse;
import com.playground.api.restaurant.model.RstrntMenuListRequest;
import com.playground.api.restaurant.model.RstrntMenuResponse;
import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.repository.RstrntMenuRepository;
import com.playground.api.restaurant.repository.RstrntRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RstrntService {
  private final RstrntMenuService rstrntMenuService;

  private final RstrntRepository rstrntRepository;

  private final RstrntMenuRepository rstrntMenuRepository;

  @Transactional(readOnly = true)
  public List<RstrntSrchResponse> getRstrntList(RstrntSrchRequest req) {
    return rstrntRepository.findAll(RstrntEntity.builder().rstrntKndCode(req.getRestaurantKindCode()).rstrntNm(req.getRestaurantName()).build());
  }

  @Transactional(readOnly = true)
  public RstrntDetailSrchResponse getRstrntDetail(RstrntDetailSrchRequest req) {
    RstrntDetailSrchResponse rstrntDetailSrchResponse = rstrntRepository.findByIdDetail(req.getRestaurantSerialNo());

    if (rstrntDetailSrchResponse != null) {
      List<RstrntMenuResponse> menuList =
          rstrntMenuService.getRstrntMenuList(RstrntMenuListRequest.builder().restaurantSerialNo(req.getRestaurantSerialNo()).build());
      rstrntDetailSrchResponse.setMenuList(menuList);
    }

    return rstrntDetailSrchResponse;
  }


  @Transactional
  public RstrntSrchResponse addRstrnt(RstrntSrchRequest req) {
    RstrntEntity entity =
        RstrntEntity.builder().rstrntNm(req.getRestaurantName()).rstrntSn(req.getRestaurantSerialNo()).rstrntKndCode(req.getRestaurantKindCode())
            .rstrntDstnc(req.getRestaurantDistance()).kakaoMapId(req.getKakaoMapId()).laLc(req.getLa()).loLc(req.getLo()).build();
    rstrntRepository.save(entity);
    return RstrntSrchResponse.builder().restaurantSerialNo(entity.getRstrntSn()).restaurantName(entity.getRstrntNm())
        .restaurantKindCode(entity.getRstrntKndCode()).restaurantDistance(entity.getRstrntDstnc()).recentChoiseDate(entity.getRecentChoiseDt())
        .accumulationChoiseCount(entity.getAccmltChoiseCo()).la(entity.getLaLc()).lo(entity.getLoLc()).kakaoMapId(entity.getKakaoMapId())
        .imageFileId(entity.getRstrntImageFileSn()).build();
  }


  @Transactional
  public void removeRstrnt(List<RstrntSrchRequest> req) {

    // TODO loop가 아닌 한방 쿼리로 변경 필요
    req.forEach(rstrnt -> {
      rstrntRepository.delete(RstrntEntity.builder().rstrntSn(rstrnt.getRestaurantSerialNo()).build());

      rstrntMenuRepository.deleteByRstrntSn(rstrnt.getRestaurantSerialNo());
    });

  }

}
