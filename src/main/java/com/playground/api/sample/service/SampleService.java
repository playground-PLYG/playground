package com.playground.api.sample.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.playground.api.sample.entity.SampleUserEntity;
import com.playground.api.sample.entity.SmpleEntity;
import com.playground.api.sample.entity.specification.SampleSpecification;
import com.playground.api.sample.model.SampleUserResponse;
import com.playground.api.sample.model.SampleUserSearchRequest;
import com.playground.api.sample.model.SmpleRequest;
import com.playground.api.sample.model.SmpleResponse;
import com.playground.api.sample.repository.SampleUserRepository;
import com.playground.api.sample.repository.SmpleDetailDetailRepository;
import com.playground.api.sample.repository.SmpleDetailRepository;
import com.playground.api.sample.repository.SmpleRepository;
import com.playground.api.sample.repository.dsl.SmpleRepositoryCustom;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleService {
  private final SampleSpecification sampleSpecification;
  private final SampleUserRepository sampleRepository;
  private final SmpleRepository smplRepository;
  private final SmpleDetailRepository smplDetailRepository;
  private final SmpleDetailDetailRepository smplDetailDetailRepository;
  private final ModelMapper modelMapper;
  private final SmpleRepositoryCustom smpleRepositoryCustom;

  public Page<SampleUserResponse> getUserPageList(SampleUserSearchRequest req, Pageable pageable) {
    SampleUserEntity sampleUserEntity = modelMapper.map(req, SampleUserEntity.class);

    Page<SampleUserEntity> sampleRepositoryPage = sampleRepository.findAll(sampleSpecification.searchCondition(sampleUserEntity), pageable);

    List<SampleUserResponse> sampleUserList =
        sampleRepositoryPage.getContent().stream().map(entity -> modelMapper.map(entity, SampleUserResponse.class)).toList();

    return new PageImpl<>(sampleUserList, sampleRepositoryPage.getPageable(), sampleRepositoryPage.getTotalElements());
  }

  /**
   * 샘플 목록 조회
   * 
   * @return List<SmpleResponse> - 조회한 샘플 목록
   */
  public List<SmpleResponse> getSmpleList() {
    List<SmpleEntity> smpleEntityList = smplRepository.findAll();

    return smpleEntityList.stream().map(entity -> SmpleResponse.builder().sampleSsno(entity.getSmpleSn()).sampleContent1(entity.getSmpleFirstCn())
        .sampleContent2(entity.getSmpleSeconCn()).sampleContent3(entity.getSmpleThrdCn()).build()).toList();
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 다건
   */
  public SmpleResponse getSmpleDsl(SmpleRequest req) {
    
    SmpleEntity smpleEntity = smpleRepositoryCustom.getSmpleSn(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3());

    return SmpleResponse.builder()
        .sampleContent1(smpleEntity.getSmpleFirstCn())
        .sampleContent2(smpleEntity.getSmpleSeconCn())
        .sampleContent3(smpleEntity.getSmpleThrdCn()).build(); 
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return List<SmpleResponse> - 조회한 샘플 다건
   */
  public List<SmpleResponse> getSmpleDslList(SmpleRequest req) {
    
    List<SmpleEntity> smpleEntityList = smpleRepositoryCustom.getSmpleSnList(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3());
  
    return smpleEntityList.stream().map(entity -> SmpleResponse.builder().sampleSsno(entity.getSmpleSn()).sampleContent1(entity.getSmpleFirstCn())
        .sampleContent2(entity.getSmpleSeconCn()).sampleContent3(entity.getSmpleThrdCn()).build()).toList();
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return Page<SmpleResponse> - 조회한 샘플 다건 페이징
   */
  public Page<SmpleResponse> getSmpleDslPageList(SmpleRequest req, Pageable pageable) {
    
    Page<SmpleEntity> smplePageList = smpleRepositoryCustom.getSmpleSnPageList(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3(), pageable);
    
    List<SmpleResponse> sampleList =
        smplePageList.getContent().stream().map(entity -> SmpleResponse.builder()
            .sampleSsno(entity.getSmpleSn())
            .sampleContent1(entity.getSmpleFirstCn())
            .sampleContent2(entity.getSmpleSeconCn())
            .sampleContent3(entity.getSmpleThrdCn())
            .build())
        .toList();
    
    return new PageImpl<>(sampleList, smplePageList.getPageable(), smplePageList.getTotalElements());
  }
  
}
