package com.playground.api.code.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.code.entity.CodeEntity;
import com.playground.api.code.entity.CodeEntity.CodeEntityBuilder;
import com.playground.api.code.model.CodeGroupSrchRequest;
import com.playground.api.code.model.CodeResponse;
import com.playground.api.code.model.CodeSearchRequest;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.repository.CodeRepository;
import com.playground.constants.CacheType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {
  private final CodeRepository codeRepository;
  private final ModelMapper modelMapper;

  /*
   * 코드조회
   */
  @Cacheable(cacheManager = CacheType.ONE_HOUR, cacheNames = "codes",
      key = "#reqData.codeSn + '_' + #reqData.codeId + '_' + #reqData.codeNm + '_' + #reqData.upperCodeId + '_' + #reqData.groupCodeAt",
      unless = "#result == null")
  @Transactional(readOnly = true)
  public List<CodeResponse> getCodePageList(CodeSearchRequest reqData) {
    List<CodeEntity> codeRepositoryPage = codeRepository.findAll(CodeEntity.builder().codeId(reqData.getCodeId()).codeNm(reqData.getCodeNm())
        .upperCodeId(reqData.getUpperCodeId()).groupCodeAt(reqData.getGroupCodeAt()).build());

    return new ArrayList<>(codeRepositoryPage.stream().map(item -> modelMapper.map(item, CodeResponse.class)).toList());
  }


  /*
   * 상위코드조회
   */
  @Transactional(readOnly = true)
  public List<CodeResponse> selectUpCodeid() {
    List<CodeEntity> upCodeList = codeRepository.findAll();

    log.debug("upCodeList: {}", upCodeList);

    return upCodeList.stream().map(item -> modelMapper.map(item, CodeResponse.class)).toList();
  }

  /*
   * 코드삭제
   */
  @CacheEvict(cacheNames = {"code", "codes", "code_group"})
  public void deleteCode(CodeSearchRequest req) {
    Integer codeSn = req.getCodeSn();

    codeRepository.delete(CodeEntity.builder().codeSn(codeSn).build());
  }

  /*
   * 코드등록/수정
   */
  @CacheEvict(cacheNames = {"code", "codes", "code_group"})
  public CodeResponse saveCodeList(CodeSearchRequest req) {
    String groupCdYn = req.getGroupCodeAt();

    CodeEntityBuilder codeEntityBuilder = CodeEntity.builder().codeSn(req.getCodeSn()).codeId(req.getCodeId()).codeNm(req.getCodeNm())
        .groupCodeAt(groupCdYn).sortOrdr(req.getSortOrdr());

    if ("N".equals(groupCdYn)) {
      String up = req.getUpperCodeId();
      CodeEntity upCode = codeRepository.findByCodeNm(up);

      codeEntityBuilder.upperCodeId(upCode.getCodeId());
    }

    CodeEntity codeEntity = codeEntityBuilder.build();

    CodeEntity saveCode = codeRepository.save(codeEntity);

    return modelMapper.map(saveCode, CodeResponse.class);
  }


  /**
   * 코드 조회
   *
   * @param reqData
   * @return CodeSrchResponse
   */
  @Cacheable(cacheManager = CacheType.ONE_HOUR, cacheNames = "code", key = "#reqData.upperCode + '_' + #reqData.code", unless = "#result == null")
  @Transactional(readOnly = true)
  public CodeSrchResponse getCode(CodeSrchRequest reqData) {
    CodeEntity codeEntity = codeRepository.findFirstByCodeIdAndUpperCodeId(reqData.getCode(), reqData.getUpperCode()).orElseGet(CodeEntity::new);

    return CodeSrchResponse.builder().code(codeEntity.getCodeId()).codeName(codeEntity.getCodeNm()).upperCode(codeEntity.getUpperCodeId())
        .order(codeEntity.getSortOrdr()).build();
  }

  /**
   * 상위코드 기준 코드 목록 조회
   *
   * @param reqData
   * @return List<CodeSrchResponse>
   */
  @Cacheable(cacheManager = CacheType.ONE_HOUR, cacheNames = "code_group", key = "#reqData.upperCode", unless = "#result == null")
  @Transactional(readOnly = true)
  public List<CodeSrchResponse> getCodeGroupList(CodeGroupSrchRequest reqData) {
    List<CodeEntity> codeEntityList = codeRepository.findByUpperCodeIdOrderBySortOrdr(reqData.getUpperCode());

    if (CollectionUtils.isNotEmpty(codeEntityList)) {
      /*
       * Stream.toList() 사용 시 Cache Serialize 문제가 발생해 우선 ArrayList로 wrapping 처리 - 참고) https://github.com/spring-projects/spring-data-redis/issues/2697
       */
      return new ArrayList<>(codeEntityList.stream().map(codeEntity -> CodeSrchResponse.builder().code(codeEntity.getCodeId())
          .codeName(codeEntity.getCodeNm()).upperCode(codeEntity.getUpperCodeId()).order(codeEntity.getSortOrdr()).build()).toList());
    } else {
      return new ArrayList<>();
    }
  }
}
