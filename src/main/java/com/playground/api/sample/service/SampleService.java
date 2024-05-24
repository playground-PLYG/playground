package com.playground.api.sample.service;

import java.util.Collections;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.sample.entity.SampleUserEntity;
import com.playground.api.sample.entity.SmpleDetailDetailEntity;
import com.playground.api.sample.entity.SmpleDetailEntity;
import com.playground.api.sample.entity.SmpleDetailPK;
import com.playground.api.sample.entity.SmpleEntity;
import com.playground.api.sample.entity.specification.SampleSpecification;
import com.playground.api.sample.model.GroupByResponse;
import com.playground.api.sample.model.JoinResponse;
import com.playground.api.sample.model.SampleUserResponse;
import com.playground.api.sample.model.SampleUserSearchRequest;
import com.playground.api.sample.model.SmpleDetailDetailRequest;
import com.playground.api.sample.model.SmpleDetailDetailResponse;
import com.playground.api.sample.model.SmpleDetailRequest;
import com.playground.api.sample.model.SmpleDetailResponse;
import com.playground.api.sample.model.SmpleRequest;
import com.playground.api.sample.model.SmpleResponse;
import com.playground.api.sample.repository.SampleUserRepository;
import com.playground.api.sample.repository.SmpleDetailDetailRepository;
import com.playground.api.sample.repository.SmpleDetailRepository;
import com.playground.api.sample.repository.SmpleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleService {
  private final SampleSpecification sampleSpecification;
  private final SampleUserRepository sampleRepository;
  private final SmpleRepository smpleRepository;
  private final SmpleDetailRepository smpleDetailRepository;
  private final SmpleDetailDetailRepository smpleDetailDetailRepository;
  private final ModelMapper modelMapper;

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
  public Page<SmpleResponse> getSmpleList(Pageable pageable) {
    Page<SmpleEntity> smpleEntityList = smpleRepository.findAllByOrderBySmpleSn(pageable);
    
    List<SmpleResponse> smpleResponse =
        smpleEntityList.getContent().stream().map(entity -> SmpleResponse.builder()
            .sampleSsno(entity.getSmpleSn())
            .sampleContent1(entity.getSmpleFirstCn())
            .sampleContent2(entity.getSmpleSeconCn())
            .sampleContent3(entity.getSmpleThrdCn())
            .build()
            ).toList();

    return new PageImpl<>(smpleResponse, smpleEntityList.getPageable(), smpleEntityList.getTotalElements());
    
  }

  /**
   * 샘플 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleResponse - 조회한 샘플 단건
   */
  public SmpleResponse getSmpleDetail(SmpleRequest reqData) {
    if (reqData != null) {
      SmpleEntity smpleEntity = smpleRepository.findById(reqData.getSampleSsno()).orElse(SmpleEntity.builder().build());

      return SmpleResponse.builder().sampleSsno(smpleEntity.getSmpleSn()).sampleContent1(smpleEntity.getSmpleFirstCn())
          .sampleContent2(smpleEntity.getSmpleSeconCn()).sampleContent3(smpleEntity.getSmpleThrdCn()).build();
    } else {
      return new SmpleResponse();
    }
  }

  /**
   * 샘플 상세 목록 조회
   *
   * @param reqData - 조회 조건
   * @return List<SmpleDetailResponse> - 조회한 샘플 상세 목록
   */
  public List<SmpleDetailResponse> getSmpleDetailList(SmpleDetailRequest reqData) {
    /*
     * TODO QueryDSL 작업 후 반영 예정 
     */
     List<SmpleDetailEntity> smpleDetailEntityList = smpleDetailRepository.findBySmpleEntity(
         SmpleEntity.builder()
         .smpleSn(reqData.getSampleSsno())
         .build());
     
     return smpleDetailEntityList.stream().map(entity -> SmpleDetailResponse.builder()
         .sampleSsno(entity.getSmpleEntity().getSmpleSn())
         .sampleDetailSsno(entity.getSmpleDetailSn())
         .sampleDetailContent1(entity.getSmpleDetailFirstCn())
         .sampleDetailContent2(entity.getSmpleDetailSeconCn())
         .sampleDetailContent3(entity.getSmpleDetailThrdCn())
         .build())
         .toList();
     
  }

  /**
   * 샘플 상세 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleDetailResponse - 조회한 샘플 상세 단건
   */
  public SmpleDetailResponse getSmpleDetailDetail(SmpleDetailRequest reqData) {
    SmpleDetailEntity smpleDetailEntity =
        smpleDetailRepository.findById(SmpleDetailPK.builder()
            .smpleEntity(reqData.getSampleDetailSsno())
            .smpleDetailSn(reqData.getSampleDetailSsno()).build())
            .orElse(SmpleDetailEntity.builder().build());

    return SmpleDetailResponse.builder().sampleSsno(smpleDetailEntity.getSmpleEntity().getSmpleSn()).sampleDetailSsno(smpleDetailEntity.getSmpleDetailSn())
        .sampleDetailContent1(smpleDetailEntity.getSmpleDetailFirstCn()).sampleDetailContent2(smpleDetailEntity.getSmpleDetailSeconCn())
        .sampleDetailContent3(smpleDetailEntity.getSmpleDetailThrdCn()).build();
  }

  /**
   * 샘플 상세 상세 목록 조회
   *
   * @param reqData - 조회 조건
   * @return List<SmpleDetailDetailResponse> - 조회한 샘플 상세 상세 목록
   */
  public List<SmpleDetailDetailResponse> getSmpleDetailDetailList(SmpleDetailDetailRequest reqData) {
    return Collections.emptyList();
    /*
     * TODO QueryDSL 작업 후 반영 예정 List<SmpleDetailDetailEntity> smpleDetailDetailEntityList = smpleDetailDetailRepository.findAllById(SmpleDetailDetailPK.builder().smpleSn(reqData.getSampleSsno()) .smpleDetailSn(reqData.getSampleDetailSsno()).smpleDetailDetailSn(reqData.getSampleDetailDetailSsno()).build());
     *
     * return smpleDetailDetailEntityList.stream() .map(entity -> SmpleDetailDetailResponse.builder().sampleSsno(entity.getSmpleSn()).sampleDetailSsno(entity.getSmpleDetailSn()) .sampleDetailDetailSsno(entity.getSmpleDetailDetailSn()).sampleDetailDetailContent1(entity.getSmpleDetailDetailFirstCn()) .sampleDetailDetailContent2(entity.getSmpleDetailDetailSeconCn()).sampleDetailDetailContent3(entity.getSmpleDetailDetailThrdCn()).build()) .toList();
     */
  }

  /**
   * 샘플 상세 상세 단건 조회
   *
   * @param reqData - 조회 조건
   * @return SmpleDetailDetailResponse - 조회한 샘플 상세 상세 단건 목록
   */
  public SmpleDetailDetailResponse getSmpleDetailDetailDetail(SmpleDetailDetailRequest reqData) {
    /*
    SmpleDetailDetailEntity smpleDetailDetailEntity = smpleDetailDetailRepository
        .findById(SmpleDetailDetailPK.builder().smpleSn(reqData.getSampleSsno()).smpleDetailSn(reqData.getSampleDetailSsno())
            .smpleDetailDetailSn(reqData.getSampleDetailDetailSsno()).build())
        .orElse(SmpleDetailDetailEntity.builder().build());

    return SmpleDetailDetailResponse.builder().sampleSsno(smpleDetailDetailEntity.getSmpleSn())
        .sampleDetailSsno(smpleDetailDetailEntity.getSmpleDetailSn()).sampleDetailDetailSsno(smpleDetailDetailEntity.getSmpleDetailDetailSn())
        .sampleDetailDetailContent1(smpleDetailDetailEntity.getSmpleDetailDetailFirstCn())
        .sampleDetailDetailContent2(smpleDetailDetailEntity.getSmpleDetailDetailSeconCn())
        .sampleDetailDetailContent3(smpleDetailDetailEntity.getSmpleDetailDetailThrdCn()).build();
        */
    return null;
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return SmpleResponse - 조회한 샘플 다건
   */
  public SmpleResponse getSmpleDsl(SmpleRequest req) {
    
    SmpleEntity smpleEntity = smpleRepository.getSmpleSn(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3());

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
    
    List<SmpleEntity> smpleEntityList = smpleRepository.getSmpleSnList(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3());
  
    return smpleEntityList.stream().map(entity -> SmpleResponse.builder().sampleSsno(entity.getSmpleSn()).sampleContent1(entity.getSmpleFirstCn())
        .sampleContent2(entity.getSmpleSeconCn()).sampleContent3(entity.getSmpleThrdCn()).build()).toList();
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return Page<SmpleResponse> - 조회한 샘플 다건 페이징
   */
  public Page<SmpleResponse> getSmpleDslPageList(SmpleRequest req, Pageable pageable) {
    
    Page<SmpleEntity> smplePageList = smpleRepository.getSmpleSnPageList(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3(), pageable);
    
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
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return List<SmpleResponse> - 조회한 샘플 다건
   */
  public List<JoinResponse> getSmpleDslJoinList(SmpleRequest req) {
    return smpleRepository.getSmpleSnJoinList(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3());
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return List<SmpleResponse> - 조회한 샘플 다건
   */
  @Transactional
  public List<GroupByResponse> getSmpleDslGroupbyList(SmpleRequest req) {
    return smpleRepository.getSmpleDslGroupbyList(req.getSampleContent1(), req.getSampleContent2(), req.getSampleContent3());
  }
  
  /**
   * 샘플 QueryDsl 실행 테스트
   *
   * @return List<SmpleResponse> - 조회한 샘플 다건
   */
  public SmpleDetailResponse addSmpleDetail(SmpleDetailRequest req) {
    
    SmpleDetailEntity res = smpleDetailRepository.save(SmpleDetailEntity.builder()
        .smpleEntity(SmpleEntity.builder().smpleSn(req.getSampleSsno()).build())
        .smpleDetailFirstCn(req.getSampleDetailContent1())
        .smpleDetailSeconCn(req.getSampleDetailContent2())
        .smpleDetailThrdCn(req.getSampleDetailContent3())
        .build()); 
    
    return SmpleDetailResponse.builder()
        .sampleSsno(res.getSmpleEntity().getSmpleSn())
        .sampleDetailSsno(res.getSmpleDetailSn())
        .sampleDetailContent1(res.getSmpleDetailFirstCn())
        .sampleDetailContent2(res.getSmpleDetailSeconCn())
        .sampleDetailContent3(res.getSmpleDetailThrdCn())
        .build();
  }
  
  public SmpleDetailResponse modifySmpleDetail(SmpleDetailRequest req) {
    SmpleDetailEntity res = smpleDetailRepository.save(SmpleDetailEntity.builder()
        .smpleEntity(SmpleEntity.builder().smpleSn(req.getSampleSsno()).build())
        .smpleDetailSn(req.getSampleDetailSsno())
        .smpleDetailFirstCn(req.getSampleDetailContent1())
        .smpleDetailSeconCn(req.getSampleDetailContent2())
        .smpleDetailThrdCn(req.getSampleDetailContent3())
        .build()); 
    
    return SmpleDetailResponse.builder()
        .sampleSsno(res.getSmpleEntity().getSmpleSn())
        .sampleDetailSsno(res.getSmpleDetailSn())
        .sampleDetailContent1(res.getSmpleDetailFirstCn())
        .sampleDetailContent2(res.getSmpleDetailSeconCn())
        .sampleDetailContent3(res.getSmpleDetailThrdCn())
        .build();
  
  }
  
  public void removeSmpleDetail(SmpleDetailRequest req) {
    smpleDetailRepository.deleteById(SmpleDetailPK.builder()
        .smpleEntity(req.getSampleSsno())
        .smpleDetailSn(req.getSampleDetailSsno())
        .build());
  }
  
  public SmpleDetailDetailResponse addSmpleDetailDetail(SmpleDetailDetailRequest req) {
    SmpleDetailDetailEntity res = smpleDetailDetailRepository.save(SmpleDetailDetailEntity.builder()
        .smpleDetailEntity(SmpleDetailEntity.builder().smpleEntity(SmpleEntity.builder().smpleSn(req.getSampleSsno()).build())
            .smpleDetailSn(req.getSampleDetailSsno()).build())
        .smpleDetailDetailFirstCn(req.getSampleDetailDetailContent1())
        .smpleDetailDetailSeconCn(req.getSampleDetailDetailContent2())
        .smpleDetailDetailThrdCn(req.getSampleDetailDetailContent3())
        .build());
    
    return SmpleDetailDetailResponse.builder()
        .sampleSsno(res.getSmpleDetailEntity().getSmpleEntity().getSmpleSn())
        .sampleDetailSsno(res.getSmpleDetailEntity().getSmpleDetailSn())
        .sampleDetailDetailSsno(res.getSmpleDetailDetailSn())
        .sampleDetailDetailContent1(res.getSmpleDetailDetailFirstCn())
        .sampleDetailDetailContent2(res.getSmpleDetailDetailSeconCn())
        .sampleDetailDetailContent3(res.getSmpleDetailDetailThrdCn())
        .build();
  }
  
  public SmpleDetailDetailResponse modifySmpleDetailDetail(SmpleDetailDetailRequest req) {
    SmpleDetailDetailEntity res = smpleDetailDetailRepository.save(SmpleDetailDetailEntity.builder()
        .smpleDetailEntity(SmpleDetailEntity.builder().smpleEntity(SmpleEntity.builder().smpleSn(req.getSampleSsno()).build())
            .smpleDetailSn(req.getSampleDetailSsno()).build())
        .smpleDetailDetailSn(req.getSampleDetailDetailSsno())
        .smpleDetailDetailFirstCn(req.getSampleDetailDetailContent1())
        .smpleDetailDetailSeconCn(req.getSampleDetailDetailContent2())
        .smpleDetailDetailThrdCn(req.getSampleDetailDetailContent3())
        .build());
    
    return SmpleDetailDetailResponse.builder()
        .sampleSsno(res.getSmpleDetailEntity().getSmpleEntity().getSmpleSn())
        .sampleDetailSsno(res.getSmpleDetailEntity().getSmpleDetailSn())
        .sampleDetailDetailSsno(res.getSmpleDetailDetailSn())
        .sampleDetailDetailContent1(res.getSmpleDetailDetailFirstCn())
        .sampleDetailDetailContent2(res.getSmpleDetailDetailSeconCn())
        .sampleDetailDetailContent3(res.getSmpleDetailDetailThrdCn())
        .build();
    
  }
  
}
