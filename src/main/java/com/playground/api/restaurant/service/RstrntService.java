package com.playground.api.restaurant.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.restaurant.entity.RstrntEntity;
import com.playground.api.restaurant.entity.RstrntFileEntity;
import com.playground.api.restaurant.model.RstrntAddRequest;
import com.playground.api.restaurant.model.RstrntDetailSrchRequest;
import com.playground.api.restaurant.model.RstrntDetailSrchResponse;
import com.playground.api.restaurant.model.RstrntExistCheckRequest;
import com.playground.api.restaurant.model.RstrntFileResponse;
import com.playground.api.restaurant.model.RstrntMenuListRequest;
import com.playground.api.restaurant.model.RstrntMenuResponse;
import com.playground.api.restaurant.model.RstrntModifyImageRequest;
import com.playground.api.restaurant.model.RstrntRemoveRequest;
import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.repository.RstrntFileRepository;
import com.playground.api.restaurant.repository.RstrntMenuHashtagMapngRepository;
import com.playground.api.restaurant.repository.RstrntMenuRepository;
import com.playground.api.restaurant.repository.RstrntRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RstrntService {
  private final RstrntMenuService rstrntMenuService;

  private final RstrntRepository rstrntRepository;

  private final RstrntMenuRepository rstrntMenuRepository;

  private final RstrntFileRepository rstrntFileRepository;

  private final RstrntMenuHashtagMapngRepository rstrntMenuHashtagMapngRepository;

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


      List<RstrntFileResponse> fileList = rstrntFileRepository.findAllByRstrntSn(req.getRestaurantSerialNo());
      rstrntDetailSrchResponse.setFileList(fileList);

    }

    return rstrntDetailSrchResponse;
  }

  @Transactional
  public RstrntSrchResponse addRstrnt(RstrntAddRequest req) {

    log.debug("RstrntSrchRequest: {}", req);

    RstrntEntity entity =
        RstrntEntity.builder().rstrntNm(req.getRestaurantName()).rstrntSn(req.getRestaurantSerialNo()).rstrntKndCode(req.getRestaurantKindCode())
            .rstrntDstnc(req.getRestaurantDistance()).kakaoMapId(req.getKakaoMapId()).laLc(req.getLa()).loLc(req.getLo()).build();

    rstrntRepository.save(entity);


    // 이미지 파일 처리
    if (req.getImageFileId() != null) {
      // 이미지 파일 ID를 사용하여 RstrntFileEntity 생성 및 저장

      RstrntFileEntity fileEntity = RstrntFileEntity.builder().fileSn(req.getImageFileId()).rstrntSn(entity.getRstrntSn()).build();
      log.debug("fileEntity: {}", fileEntity);

      rstrntFileRepository.save(fileEntity);
    }

    return RstrntSrchResponse.builder().restaurantSerialNo(entity.getRstrntSn()).restaurantName(entity.getRstrntNm())
        .restaurantKindCode(entity.getRstrntKndCode()).restaurantDistance(entity.getRstrntDstnc()).la(entity.getLaLc()).lo(entity.getLoLc())
        .kakaoMapId(entity.getKakaoMapId()).kakaoMapId(entity.getKakaoMapId()).build();
  }

  @Transactional
  public void removeRstrnt(List<RstrntRemoveRequest> req) {
    List<Integer> rstrntSnList = req.stream().mapToInt(RstrntRemoveRequest::getRestaurantSerialNo).boxed().toList();

    rstrntRepository.deleteAllByRstrntSnIn(rstrntSnList);
    rstrntMenuRepository.deleteAllByRstrntSnIn(rstrntSnList);
    rstrntMenuHashtagMapngRepository.deleteAllByRstrntSnIn(rstrntSnList);
  }

  @Transactional(readOnly = true)
  public RstrntSrchResponse getIsExist(RstrntExistCheckRequest req) {
    RstrntEntity rstrntEntity = rstrntRepository.findFirstByRstrntNmOrKakaoMapId(req.getRestaurantName(), req.getKakaoMapId()).orElse(null);

    if (rstrntEntity == null) {
      return null;
    } else {
      return RstrntSrchResponse.builder().restaurantName(rstrntEntity.getRstrntNm()).kakaoMapId(rstrntEntity.getKakaoMapId()).build();
    }
  }


  @Transactional
  public void modifyRstrntImage(RstrntModifyImageRequest req) {
    rstrntRepository.updateRstrntImageFileSnById(req.getRestaurantSerialNo(), req.getImageFileId());
  }
}
