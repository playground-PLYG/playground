package com.playground.api.code.model;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "CodeRequest", description = "요청 데이터")
@Getter
@Setter
public class CodeSearchRequest extends BaseDto {

	@Schema(description = "코드ID")
	private String cdId;
  
	@Schema(description = "코드명")
	private String cdNm;
	 
	@Schema(description = "상위코드ID")
	private String upCdId;
	 
	@Schema(description = "그룹코드여부")
	private String groupCdYn;
	
	@Schema(description = "정렬순번")
	private String sortSn;
	
	@Schema(description = "등록자")
	private String regMbrNo;
	
	@Schema(description = "수정자")
	private String mdfcnMbrNo;
	 


}
