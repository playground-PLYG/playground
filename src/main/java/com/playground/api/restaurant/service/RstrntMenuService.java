package com.playground.api.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.hashtag.entity.HashtagEntity;
import com.playground.api.hashtag.repository.HashtagRepository;
import com.playground.api.restaurant.entity.RstrntMenuEntity;
import com.playground.api.restaurant.entity.RstrntMenuEntity.RstrntMenuEntityBuilder;
import com.playground.api.restaurant.entity.RstrntMenuHashtagMapngEntity;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RstrntMenuService {
  private final RstrntMenuRepository rstrntMenuRepository;
  private final HashtagRepository hashtagRepository;
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
    RstrntMenuEntity rstrntMenuEntity = rstrntMenuRepository.save(RstrntMenuEntity.builder().rstrntSn(reqData.getRestaurantSerialNo())
        .rstrntMenuNm(reqData.getMenuName()).rstrntMenuPc(reqData.getMenuPrice()).build());
    
    /*
     * 해시태그가 있는 지 조회 후 등록
     * 1. 해시태그 전체를 조회 후 화면에서 받은 거랑 비교해서 다른 것만 넣을지
     * 2. 화면에서 받은거 전체를 merge로 엎어치는게 좋을지
     * 뭐가 좋을까나
     */
    //1번 먼저 해보자
    List<HashtagEntity> hashtagRes = hashtagRepository.findAll();
    List<String> allHashtagList = hashtagRes.stream().map(e -> e.getHashtagNm()).collect(Collectors.toCollection(ArrayList::new));
    log.debug("전체 해시태그 ::: {}", allHashtagList);
    log.debug("화면에서 보낸 해시태그 ::: {}", reqData.getHashtagList());
    
    List<String> addHashtagList = reqData.getHashtagList().stream().filter(val -> allHashtagList.stream()
        .noneMatch(Predicate.isEqual(val))).collect(Collectors.toList());
    
    log.debug("중복 제거된 해시태그 목록 ::: {}", addHashtagList);
    
    List<HashtagEntity> dupHashTagList = hashtagRes.stream().filter(e -> reqData.getHashtagList().stream()
        .noneMatch(Predicate.isEqual(e))).collect(Collectors.toList());
    
    log.debug("이미 있는 해시태그 목록 ::: {}", dupHashTagList);
    
    //중복된 해시태그 추가 및 등록
    List<HashtagEntity> saveAllHashtag = new ArrayList<>();
    for(String hashtagNm : addHashtagList) {
      saveAllHashtag.add(HashtagEntity.builder().hashtagNm(hashtagNm).build());
    }
    
    log.debug("중복 제거된 해시태그 저장목록 ::: {}", saveAllHashtag);
    List<HashtagEntity> saveAllHashtagRes = hashtagRepository.saveAll(saveAllHashtag);
    
    /*
     * 해시태그 매핑테이블 등록
     */
    //중복 제거된 해시태그 추가
    List<RstrntMenuHashtagMapngEntity> saveMapngList = new ArrayList<>();
    for(HashtagEntity addHashtag : saveAllHashtagRes) {
      saveMapngList.add(RstrntMenuHashtagMapngEntity.builder()
          .rstrntMenuSn(rstrntMenuEntity.getRstrntMenuSn())
          .hashtagSn(addHashtag.getHashtagSn()).build());
    }
    
    //이미 들어가있는 해시태그 추가
    for(HashtagEntity dupHashtag : dupHashTagList) {
      saveMapngList.add(RstrntMenuHashtagMapngEntity.builder()
          .rstrntMenuSn(rstrntMenuEntity.getRstrntMenuSn())
          .hashtagSn(dupHashtag.getHashtagSn()).build());
    }
    
    List<RstrntMenuHashtagMapngEntity> saveAllHashtagMapngRes = rstrntMenuHashtagMapngRepository.saveAll(saveMapngList);
    log.debug("메뉴 해시태그 매핑 리스트 ::: {}", saveAllHashtagMapngRes);
    
    //리턴 바꿔야 할 거 같은데
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
