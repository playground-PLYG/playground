package com.playground.api.restaurant.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.restaurant.entity.RstrntEntity;
import com.playground.api.restaurant.entity.specification.RstrntSpecification;
import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.repository.RstrntMenuRepository;
import com.playground.api.restaurant.repository.RstrntRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RstrntService {
  private final RstrntSpecification rstrntSpecification;
  private final RstrntRepository rstrntRepository;
  private final ModelMapper modelMapper;
  private final RstrntMenuRepository rstrntMenuRepository;

  @Transactional(readOnly = true)
  public List<RstrntSrchResponse> getRstrntList(RstrntSrchRequest req) {

    RstrntEntity getRstrnt = RstrntEntity.builder().rstrntKndCode(req.getRstrntKndCode()).rstrntNm(req.getRstrntNm()).build();

    log.debug(" 식당리스트 조회 :::: {}", getRstrnt);

    List<RstrntEntity> getRstrntList = rstrntRepository.findAll(rstrntSpecification.searchCondition(getRstrnt));

    log.debug(" 식당리스트  :::: {}", getRstrntList);

    return getRstrntList.stream().map(item -> modelMapper.map(item, RstrntSrchResponse.class)).toList();
  }


  @Transactional
  public RstrntSrchResponse addRstrnt(RstrntSrchRequest req) {
    RstrntEntity entity = RstrntEntity.builder().rstrntNm(req.getRstrntNm()).rstrntSn(req.getRstrntSn()).rstrntKndCode(req.getRstrntKndCode())
        .rstrntDstnc(req.getRstrntDstnc()).build();
    rstrntRepository.save(entity);
    return modelMapper.map(entity, RstrntSrchResponse.class);
  }


  @Transactional
  public void removeRstrnt(List<RstrntSrchRequest> req) {

    // TODO loop가 아닌 한방 쿼리로 변경 필요
    req.forEach(rstrnt -> {
      rstrntRepository.delete(RstrntEntity.builder().rstrntSn(rstrnt.getRstrntSn()).build());

      rstrntMenuRepository.deleteByRstrntSn(rstrnt.getRstrntSn());
    });

  }

}
