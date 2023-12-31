package com.playground.api.code.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public List<CodeResponse> getCodePageList(CodeSearchRequest req) {
	  
	CodeEntity codeEntity = modelMapper.map(req, CodeEntity.class);
    List<CodeEntity> codeRepositoryPage = codeRepository.findAll(codeSpecification.searchCondition(codeEntity));
    return codeRepositoryPage.stream().map(item -> modelMapper.map(item, CodeResponse.class)).toList();
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
  public void deleteCode(CodeSearchRequest req) {
	  
	 String sn =  req.getSn();
	codeRepository.delete(CodeEntity.builder().sn(sn).build());
	  
  }
  
  /*
   * 코드등록/수정
   */
  public CodeResponse saveCodeList(CodeSearchRequest req) {
	  
	  String groupCdYn =  req.getGroupCdYn();
	  log.debug("groupCdYn: {}", groupCdYn);
	  
	  if(groupCdYn.equals("N")) {
		  String up =  req.getUpCdId();
		  CodeEntity upCode = codeRepository.findByCdNm(up);
		  log.debug("upCode: {}", upCode.getCdId());
		  log.debug("upCode: {}", upCode);
		  
		  req.setUpCdId(upCode.getCdId());
	  }
	  
	  CodeEntity codeEntity = modelMapper.map(req, CodeEntity.class);
		  
	  CodeEntity saveCode = codeRepository.save(codeEntity);

	  return  modelMapper.map(saveCode, CodeResponse.class);  	  
	  
  }
  
  


  
}
