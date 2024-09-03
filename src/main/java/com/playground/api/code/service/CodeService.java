package com.playground.api.code.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.code.entity.CodeEntity;
import com.playground.api.code.model.CodeAddRequest;
import com.playground.api.code.model.CodeGroupSrchRequest;
import com.playground.api.code.model.CodeRemoveRequest;
import com.playground.api.code.model.CodeResponse;
import com.playground.api.code.model.CodeSearchRequest;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.repository.CodeRepository;
import com.playground.api.common.service.impl.CachedPageImpl;
import com.playground.constants.CacheType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {
  private final CodeRepository codeRepository;

  /**
   * 코드 리스트 조회
   *
   * @param reqData
   * @return Page<CodeResponse>
   */
  @Cacheable(cacheManager = CacheType.ONE_HOUR, cacheNames = "codes",
      key = "#reqData.code + '_' + #reqData.codeName + '_' + #reqData.upperCode + '_' + #reqData.groupCode + '_' + #pageable.getPageSize() + '_' + #pageable.getPageNumber()",
      unless = "#result == null")
  @Transactional(readOnly = true)
  public Page<CodeResponse> getCodePageList(Pageable pageable, CodeSearchRequest reqData) {

    CodeEntity entity = CodeEntity.builder().codeId(reqData.getCode()).codeNm(reqData.getCodeName()).upperCodeId(reqData.getUpperCode())
        .groupCodeAt(reqData.getGroupCode()).build();

    Page<CodeEntity> codePageList = codeRepository.getCodePageList(entity, pageable);

    List<CodeResponse> codeList = codePageList.getContent().stream()
        .map(codeEntity -> CodeResponse.builder().codeSerialNo(codeEntity.getCodeSn()).code(codeEntity.getCodeId()).codeName(codeEntity.getCodeNm())
            .upperCode(codeEntity.getUpperCodeId()).groupCode(codeEntity.getGroupCodeAt()).order(codeEntity.getSortOrdr())
            .registUsrId(codeEntity.getRegistUsrId()).registDt(codeEntity.getRegistDt()).updtUsrId(codeEntity.getUpdtUsrId())
            .updtDt(codeEntity.getUpdtDt()).codeValue(codeEntity.getCodeValue()).useYn(codeEntity.getUseAt()).build())
        .toList();

    return new CachedPageImpl<>(codeList, codePageList.getPageable(), codePageList.getTotalElements());
  }


  /**
   * 전체 코드 리스트 조회
   *
   * @return List<CodeResponse>
   */
  @Transactional(readOnly = true)
  public List<CodeResponse> getAllCodeList() {
    List<CodeEntity> allCodeList = codeRepository.findAll();

    log.debug("upCodeList: {}", allCodeList);

    if (CollectionUtils.isNotEmpty(allCodeList)) {
      return new ArrayList<>(allCodeList.stream()
          .map(codeEntity -> CodeResponse.builder().codeSerialNo(codeEntity.getCodeSn()).code(codeEntity.getCodeId()).codeName(codeEntity.getCodeNm())
              .upperCode(codeEntity.getUpperCodeId()).groupCode(codeEntity.getGroupCodeAt()).order(codeEntity.getSortOrdr())
              .codeValue(codeEntity.getCodeValue()).useYn(codeEntity.getUseAt()).build())
          .toList());
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * 코드 삭제
   *
   * @param reqData
   */
  @CacheEvict(cacheNames = {"code", "codes", "code_group"}, allEntries = true)
  @Transactional
  public void removeCode(List<CodeRemoveRequest> reqData) {
    List<Integer> codeSns = reqData.stream().map(CodeRemoveRequest::getCodeSerialNo).toList();

    log.debug("codeSns: {}", codeSns);

    codeRepository.deleteByCodeSnIn(codeSns);
  }

  /**
   * 코드 등록/수정
   *
   * @param reqData
   * @return CodeResponse
   */
  @CacheEvict(cacheNames = {"code", "codes", "code_group"}, allEntries = true)
  @Transactional
  public CodeResponse addCode(CodeAddRequest reqData) {
    CodeEntity entity = CodeEntity.builder().codeSn(reqData.getCodeSerialNo()).codeId(reqData.getCode()).codeNm(reqData.getCodeName())
        .upperCodeId(reqData.getUpperCode()).groupCodeAt(reqData.getGroupCode()).sortOrdr(reqData.getOrder()).codeValue(reqData.getCodeValue())
        .useAt(reqData.getUseYn()).build();

    log.debug("addCode entity: {}", entity);


    codeRepository.save(entity);

    return CodeResponse.builder().codeSerialNo(entity.getCodeSn()).code(entity.getCodeNm()).codeName(entity.getGroupCodeAt())
        .upperCode(entity.getUpperCodeId()).groupCode(entity.getGroupCodeAt()).order(entity.getSortOrdr()).codeValue(entity.getCodeValue())
        .useYn(entity.getUseAt()).build();
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
        .order(codeEntity.getSortOrdr()).codeValue(codeEntity.getCodeValue()).useYn(codeEntity.getUseAt()).build();
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
      return new ArrayList<>(codeEntityList.stream()
          .map(codeEntity -> CodeSrchResponse.builder().code(codeEntity.getCodeId()).codeName(codeEntity.getCodeNm())
              .upperCode(codeEntity.getUpperCodeId()).order(codeEntity.getSortOrdr()).codeValue(codeEntity.getCodeValue())
              .useYn(codeEntity.getUseAt()).build())
          .toList());
    } else {
      return new ArrayList<>();
    }
  }
}
