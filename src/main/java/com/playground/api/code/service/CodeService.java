package com.playground.api.code.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.playground.api.code.entity.CodeEntity;
import com.playground.api.code.entity.specification.CodeSpecification;
import com.playground.api.code.model.CodeResponse;
import com.playground.api.code.model.CodeSearchRequest;
import com.playground.api.code.repository.CodeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {
  private final CodeSpecification codeSpecification;
  private final CodeRepository codeRepository;
  private final ModelMapper modelMapper;

  
  

/*
 * 코드조회
 */
  public Page<CodeResponse> getCodePageList(CodeSearchRequest req, Pageable pageable) {
	 
	  
	CodeEntity codeEntity = modelMapper.map(req, CodeEntity.class);

    Page<CodeEntity> codeRepositoryPage = codeRepository.findAll(codeSpecification.searchCondition(codeEntity), pageable);

    List<CodeResponse> codeList =
    		codeRepositoryPage.getContent().stream().map(entity -> modelMapper.map(entity, CodeResponse.class)).toList();

    return new PageImpl<>(codeList, codeRepositoryPage.getPageable(), codeRepositoryPage.getTotalElements());
  }
  
  /*
   * 코드삭제
   */
  public void deleteCode(CodeSearchRequest req) {
	  
	  String codeId = req.getCdId();
	  codeRepository.delete(CodeEntity.builder().cdId(codeId).build());
	  
  }
  
  /*
   * 코드등록
   */
  public CodeResponse saveCodeList(CodeSearchRequest req) {
	   
	  CodeEntity codeEntity = modelMapper.map(req, CodeEntity.class);
		  
	  CodeEntity saveCode = codeRepository.save(codeEntity);
		  
	  return  modelMapper.map(saveCode, CodeResponse.class);  	  
	  
  }
  
  
 
  /*
   * 코드수정
   */
  public CodeResponse updateCodeList(CodeSearchRequest req) {
	 
	  CodeEntity codeEntity = modelMapper.map(req, CodeEntity.class);
	  
	  CodeEntity saveCode = codeRepository.save(codeEntity);
		  
	  return  modelMapper.map(saveCode, CodeResponse.class);  	  
	  
  }
  
  
}
