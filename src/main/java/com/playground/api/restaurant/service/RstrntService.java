package com.playground.api.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.restaurant.entity.RstrntEntity;
import com.playground.api.restaurant.entity.RstrntFileEntity;
import com.playground.api.restaurant.model.RstrntAddRequest;
import com.playground.api.restaurant.model.RstrntAddResponse;
import com.playground.api.restaurant.model.RstrntDetailSrchRequest;
import com.playground.api.restaurant.model.RstrntDetailSrchResponse;
import com.playground.api.restaurant.model.RstrntExistCheckRequest;
import com.playground.api.restaurant.model.RstrntFileResponse;
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

      List<RstrntFileResponse> fileList = rstrntFileRepository.findAllByRstrntSn(req.getRestaurantSerialNo());
      rstrntDetailSrchResponse.setFileList(fileList);

    }

    return rstrntDetailSrchResponse;
  }

  @Transactional
  public RstrntAddResponse addRstrnt(RstrntAddRequest req) {

    log.debug("RstrntSrchRequest: {}", req);

    RstrntEntity entity = RstrntEntity.builder().rstrntNm(req.getRestaurantName()).rstrntKndCode(req.getRestaurantKindCode())
        .rstrntDstnc(req.getRestaurantDistance()).kakaoMapId(req.getKakaoMapId()).laLc(req.getLa()).loLc(req.getLo()).build();

    rstrntRepository.save(entity);

    // 이미지 파일 처리
    List<Integer> imageFileIdList = new ArrayList<>();
    if (req.getImageFileIds() != null && !req.getImageFileIds().isEmpty()) {
      for (Integer imageFileId : req.getImageFileIds()) {
        RstrntFileEntity fileEntity = RstrntFileEntity.builder().fileSn(imageFileId).rstrntSn(entity.getRstrntSn()).build();

        log.debug("fileEntity: {}", fileEntity);

        rstrntFileRepository.save(fileEntity);
        imageFileIdList.add(fileEntity.getFileSn());
      }
    }

    return RstrntAddResponse.builder().restaurantSerialNo(entity.getRstrntSn()).restaurantName(entity.getRstrntNm())
        .restaurantKindCode(entity.getRstrntKndCode()).restaurantDistance(entity.getRstrntDstnc()).la(entity.getLaLc()).lo(entity.getLoLc())
        .kakaoMapId(entity.getKakaoMapId()).imageFileIds(imageFileIdList).build();
  }

  @Transactional
  public void removeRstrnt(List<RstrntRemoveRequest> req) {
    List<Integer> rstrntSnList = req.stream().mapToInt(RstrntRemoveRequest::getRestaurantSerialNo).boxed().toList();

    rstrntRepository.deleteAllByRstrntSnIn(rstrntSnList);
    rstrntMenuRepository.deleteAllByRstrntSnIn(rstrntSnList);
    rstrntMenuHashtagMapngRepository.deleteAllByRstrntSnIn(rstrntSnList);
    rstrntFileRepository.deleteAllByRstrntSnIn(rstrntSnList);
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

    rstrntFileRepository.updateRstrntImageFileSnById(req.getRestaurantSerialNo(), req.getImageFileId(), req.getOldImageFileId());

    // List<Integer> imageFileIds = req.getImageFileIds();
    // Integer restaurantSerialNo = req.getRestaurantSerialNo();
    //
    // for (Integer imageFileId : imageFileIds) {
    // rstrntFileRepository.updateRstrntImageFileSnById(restaurantSerialNo, imageFileId);
    // }
  }
}
